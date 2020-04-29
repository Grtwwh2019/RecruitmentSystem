import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    currentUser: {},
    positionTypeSet: [],
    citySet: [],
    industrySet: []
  },
  mutations: {
    initCurrentUser (state, data) {
      state.currentUser = data
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
    }
  },
  actions: {

  }
})

export default store