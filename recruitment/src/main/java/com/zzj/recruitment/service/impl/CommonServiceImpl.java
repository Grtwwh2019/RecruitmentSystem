package com.zzj.recruitment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.CompanyMapper;
import com.zzj.recruitment.dao.EmploymentMapper;
import com.zzj.recruitment.service.ICommonService;
import com.zzj.recruitment.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 14:24:24
 */
@Service("commonService")
public class CommonServiceImpl implements ICommonService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    EmploymentMapper employmentMapper;

    @Autowired
    CompanyMapper companyMapper;

    /**
     * 根据type查询数据，0——搜索职位；1——搜索公司
     * @param type
     * @param searchVo
     * @param pageNum
     * @return
     */
    @Override
    public ServerResponse<PageInfo> returnListByType(Integer type, CompanyPositionSearchVo searchVo, Integer pageNum) {
        // todo 优化SQL，查询速度太慢了;
        PageHelper.startPage(pageNum, 10); // 默认一页10条数据
        // type == 0;搜索职位
        if (type == 0) {
            List<PosisitonListVo> posisitonList = employmentMapper.selectPositionBySearchVo(searchVo);
            PageInfo pageResult = new PageInfo(posisitonList);
            return ServerResponse.createResponseBySuccess("查询职位成功！", pageResult);
        } else if (type == 1) {
            List<CompanyListVo> companyList = companyMapper.selectCompanyBySearchVo(searchVo);
            PageInfo pageResult = new PageInfo(companyList);
            return ServerResponse.createResponseBySuccess("查询公司成功！", pageResult);
        }
        return ServerResponse.createResponseByErrorMsg("查询失败！");
    }

    /**
     * 根据type查询详细数据，0——职位；1——公司
     *
     * @return
     */
    @Override
    @Transactional
    public ServerResponse getDetail(Integer type, Integer id) {
        // type == 0;查询职位详情
        if (type == 0) {
            // 先判断缓存中是否存在
            PositionDetailVo positionDetail = (PositionDetailVo) redisTemplate.opsForValue().get(Const.POSITION_DETAIL + id);
            if (positionDetail != null) {
                // 如果缓存中有
                return ServerResponse.createResponseBySuccess("查询职位详情成功！", positionDetail);
            }
            // 如果没有，则查找数据库
            positionDetail = employmentMapper.selectDetailByPositionId(id);
            if (positionDetail != null) {
                // 存入到缓存中,过期时间为一天
                redisTemplate.opsForValue().set(Const.POSITION_DETAIL + id, positionDetail, 60 * 60 * 24, TimeUnit.SECONDS);
                return ServerResponse.createResponseBySuccess("查询职位详情成功！", positionDetail);
            }
        } else if (type == 1) {
            CompanyDetailVo companyDetail = (CompanyDetailVo) redisTemplate.opsForValue().get(Const.COMPANY_DETAIL + id);
            if (companyDetail != null) {
                return ServerResponse.createResponseBySuccess("查询公司详情成功！", companyDetail);
            }
            companyDetail = companyMapper.selectDetailByCompanyId(id);
            if (companyDetail != null) {
                redisTemplate.opsForValue().set(Const.COMPANY_DETAIL + id, companyDetail, 60 * 60 * 24, TimeUnit.SECONDS);
                return ServerResponse.createResponseBySuccess("查询公司详情成功！", companyDetail);
            }
        }
        return ServerResponse.createResponseByErrorMsg("查询详情失败!");
    }

}
