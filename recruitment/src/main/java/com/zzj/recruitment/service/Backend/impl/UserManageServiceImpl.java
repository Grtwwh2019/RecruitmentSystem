package com.zzj.recruitment.service.Backend.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.AnnounceMapper;
import com.zzj.recruitment.dao.CollectionMapper;
import com.zzj.recruitment.dao.UserMapper;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IUserManageService;
import com.zzj.recruitment.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-15 19:12:23
 */
@Service("userManageService")
@Transactional
public class UserManageServiceImpl implements IUserManageService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * dao层操作收藏表的mapper
     */
    @Autowired
    CollectionMapper collectionMapper;

    @Autowired
    AnnounceMapper announceMapper;

    @Autowired
    UserMapper userMapper;


    /**
     * @param positionId
     * @param userId
     * @param type：（收藏=1，取消收藏=0）
     * @return
     */
    @Override
    public ServerResponse collectPosition(Integer positionId, Integer userId, Integer type) {
        CollectionVo collectionVo = new CollectionVo();
        // 判断缓存中是否存在该用户的数据
        Set members = redisTemplate.opsForSet().members(Const.collectionCache.POSITION + userId);
        if (members == null || members.size() == 0) {
            // 如果没有，在数据库查找数据，添加到缓存中
            List<Integer> dbResult = collectionMapper.selectPositionByUserId(userId);
            for (Integer value : dbResult) {
                redisTemplate.opsForSet().add(Const.collectionCache.POSITION + userId, value);
            }
            redisTemplate.expire(Const.collectionCache.POSITION + userId, 60 * 60, TimeUnit.SECONDS);
        }
        // 如果缓存中有全部记录，判断要收藏/取消的记录是否存在
        Boolean isMember = redisTemplate.opsForSet().isMember(Const.collectionCache.POSITION + userId, positionId);
        if (isMember) {
            // 如果有，删除
            boolean remResult = this.removePosition(positionId, userId, collectionVo);
            if (remResult) {
                return ServerResponse.createResponseBySuccess("取消收藏成功", collectionVo);
            }
        } else {
            // 如果没有,则新增(如果数据库已经有呢？)
            boolean addResult = this.addPosition(positionId, userId, collectionVo);
            if (addResult) {
                return ServerResponse.createResponseBySuccess("收藏成功", collectionVo);
            }
        }
        return ServerResponse.createResponseByErrorMsg("操作失败，未知错误，请联系管理员！");
    }


    /**
     * 收藏公司
     * @param companyId
     * @param userId
     * @param type
     * @return
     */
    @Override
    public ServerResponse collectCompany(Integer companyId, Integer userId, Integer type) {
        CollectionVo collectionVo = new CollectionVo();
        // 判断缓存中是否存在该用户的数据
        Set members = redisTemplate.opsForSet().members(Const.collectionCache.COMPANY + userId);
        if (members == null || members.size() == 0) {
            // 如果没有，在数据库查找数据，添加到缓存中
            List<Integer> dbResult = collectionMapper.selectCompanyByUserId(userId);
            for (Integer value : dbResult) {
                redisTemplate.opsForSet().add(Const.collectionCache.COMPANY + userId, value);
            }
            redisTemplate.expire(Const.collectionCache.COMPANY + userId, 60 * 60, TimeUnit.SECONDS);
        }
        // 如果缓存中有全部记录，判断要收藏/取消的记录是否存在
        Boolean isMember = redisTemplate.opsForSet().isMember(Const.collectionCache.COMPANY + userId, companyId);
        if (isMember) {
            // 如果有，删除
            boolean remResult = this.removeCompany(companyId, userId, collectionVo);
            if (remResult) {
                return ServerResponse.createResponseBySuccess("取消收藏成功", collectionVo);
            }
        } else {
            // 如果没有,则新增(如果数据库已经有呢？)
            boolean addResult = this.addCompany(companyId, userId, collectionVo);
            if (addResult) {
                return ServerResponse.createResponseBySuccess("收藏成功", collectionVo);
            }
        }
        return ServerResponse.createResponseByErrorMsg("操作失败，未知错误，请联系管理员！");
    }


    /**
     * 收藏职位
     * @param positionId
     * @param userId
     * @param collectionVo
     * @return 成功返回true ， 失败返回false
     */
    private boolean addPosition(Integer positionId, Integer userId, CollectionVo collectionVo) {
        Integer isExist = collectionMapper.selectByPositionUserId(positionId, userId);
        if (isExist > 0) {
            return true;
        }
        Integer dbaddResult = collectionMapper.addByPositionUserId(positionId, userId);
        if (dbaddResult > 0) {
            Long redisaddResult = redisTemplate.opsForSet().add(Const.collectionCache.POSITION + userId, positionId);
            collectionVo.setShowStts(1);
            return true;
        } else {
            return false;
        }
    }

    private boolean addCompany(Integer companyId, Integer userId, CollectionVo collectionVo) {
        Integer isExist = collectionMapper.selectByCompanyUserId(companyId, userId);
        if (isExist > 0) {
            return true;
        }
        Integer dbaddResult = collectionMapper.addByCompanyUserId(companyId, userId);
        if (dbaddResult > 0) {
            Long redisaddResult = redisTemplate.opsForSet().add(Const.collectionCache.COMPANY + userId, companyId);
            collectionVo.setShowStts(1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取消收藏
     * @param positionId
     * @param userId
     * @param collectionVo
     * @return
     */
    private boolean removePosition(Integer positionId, Integer userId, CollectionVo collectionVo) {
        Integer dbdelResult = collectionMapper.removeByPositionUserId(positionId, userId);
        if (dbdelResult > 0) {
            Long redisremResult = redisTemplate.opsForSet().remove(Const.collectionCache.POSITION + userId, positionId);
            collectionVo.setShowStts(0);
            return true;
        } else {
            return false;
        }
    }
    private boolean removeCompany(Integer companyId, Integer userId, CollectionVo collectionVo) {
        Integer dbdelResult = collectionMapper.removeByCompanyUserId(companyId, userId);
        if (dbdelResult > 0) {
            Long redisremResult = redisTemplate.opsForSet().remove(Const.collectionCache.COMPANY + userId, companyId);
            collectionVo.setShowStts(0);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据type 获取收藏的职位/公司列表
     *
     * @param type:0——职位，1——公司
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> returnCollectionList(Integer type, User user, Integer pageNum, Integer pageSize) {
        // 1.startPage：记录一个开始页
        if (type == 0) {
            PageHelper.startPage(pageNum, pageSize);
            // 2.填充自己的sql查询逻辑
            // 展示职位
            List<PosisitonListVo> posisitonListVos = collectionMapper.getPosisitonList(user.getId());
            // 3.pageHelper的收尾：分页的结果
            PageInfo pageResult = new PageInfo(posisitonListVos);
            return ServerResponse.createResponseBySuccess("获取收藏职位列表成功！", pageResult);
        } else if (type == 1) {
            PageHelper.startPage(pageNum, pageSize);
            List<CompanyListVo> companyListVos = collectionMapper.getCompanyList(user.getId());
            PageInfo pageResult = new PageInfo(companyListVos);
            return ServerResponse.createResponseBySuccess("获取收藏公司列表成功！", pageResult);
        }
        return ServerResponse.createResponseByErrorMsg("获取失败，请联系管理员！");
    }

    /**
     * 获取用户公告列表
     * redis使用list：因为 list 结构的数据查询两端附近的数据性能非常好，
     * 所以适合一些需要获取最新数据的场景，比如新闻类应用的 “最近新闻”。
     * 在头部插入数据，性能会很高
     * @param user
     * @return
     */
    @Override
    public ServerResponse<PageInfo> returnAnnounceList(User user, Integer pageNum) {
        // 分页，默认一页15条
        PageHelper.startPage(pageNum, 15);
        List<AnnounceListVo> announceList = announceMapper.getAnnounceList(user.getId());
        if (announceList.size() >= 0) {
            // lpush 命令：将一个或多个值插入到列表头部。如果 key 不存在，则创建list，然后再插入数据操作。
            // blpop 命令：redis的list是链表结构所以BLPOP命令正是取出列表的第一个元素，如果list当中没有没有元素，会一直等待到超时，或者发现有数据为止。
//            Long listSize = redisTemplate.opsForList().size(Const.ANNOUNCELIST + user.getUsername());
//            if (listSize <= 0 || listSize == null) {
//                for (AnnounceListVo announceListVo : announceList) {
//                    redisTemplate.opsForList().leftPush(Const.ANNOUNCELIST + user.getUsername(), announceListVo);
//                }
//            }
            PageInfo pageResult = new PageInfo(announceList);
            return ServerResponse.createResponseBySuccess("获取公告列表成功！", pageResult);
        }
        return ServerResponse.createResponseByErrorMsg("获取公告列表失败，请联系管理员！");
    }


    /**
     * 获取公告详情
     *
     * @param announceId
     * @param user
     * @return
     */
    @Override
    public ServerResponse<AnnounceListVo> returnAnnounceDetail(Integer announceId, User user) {
        AnnounceListVo announceListVo = announceMapper.selectByannounceId(announceId);
        if (announceListVo != null) {
            // todo 增加对content，即公告内容 的解析逻辑
            return ServerResponse.createResponseBySuccess("获取公告详情成功！", announceListVo);
        }
//        Long listSize = redisTemplate.opsForList().size(Const.ANNOUNCELIST + user.getUsername());
        // 如果缓存中没有，则在数据库找
//        if (listSize == null || listSize <= 0) {
//        }
        // 否则，直接从缓存中返回
//        redisTemplate.opsForList()
        return ServerResponse.createResponseByErrorMsg("获取公告详情失败，该用户不存在相关公告！");
    }


    /**
     * 提交企业用户认证申请
     *
     * @param enterpriseUserInfoVo      所属公司id
     * @param enterpriseUserInfoVo          工号
     * @param enterpriseUserInfoVo      工牌
     * @param enterpriseUserInfoVo 企业邮箱
     * @return
     */
    @Override
    public ServerResponse applyEnterpriseUser(EnterpriseUserInfoVo enterpriseUserInfoVo, User user) {
        // 直接保存
        enterpriseUserInfoVo.setUserId(user.getId());
        enterpriseUserInfoVo.setNickname(user.getNickname());
        enterpriseUserInfoVo.setTelephone(user.getTelephone());
        Integer result = userMapper.saveEnterpriseUserInfo(enterpriseUserInfoVo);
        if (result > 0) {
            return ServerResponse.createResponseBySuccessMsg("提交申请成功！");
        }
        return ServerResponse.createResponseByErrorMsg("提交申请失败！");
    }
}


