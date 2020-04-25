import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    routes: [], // 路由数组
    selectedCity: {'code':0, 'name':'广州站'},
    positionTypeSet: [],
    citySet: [],
    industrySet: [],
    user: {}
  },
  mutations: {
    initRoutes(state, data) {
      state.routes = data
    },
    initSelectedCity(state, data) {
      console.log(state)
      console.log(data)
    },
    initPositionTypeSet(state, data) {
      state.positionTypeSet = data
      // console.log(state.positionTypeSet)
    },
    initCitySet(state, data) {
      state.citySet = data
    },
    initIndustrySet(state, data) {
      state.industrySet = data
    },
    initUser(state, data) {
      state.user = data
    }
  },
  actions: {

  }
})