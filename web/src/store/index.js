import Vue from 'vue'
import Vuex from 'vuex'
import {nanoid} from "nanoid";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {

    // 题目列表
    questionList:[],
    //非选择题
    unChoiceType:[],

    // 当前用户名
    username:"",
    // 存储token
    Authorization:localStorage.getItem("Authorization") ? localStorage.getItem("Authorization") : '',
  },
  mutations: {
    // 保存当前用户名
    SET_USERNAME(state,username){
      console.log("username:"+username)
      state.username = username
    },

    // 保存token
    SET_TOKEN(state,value){
      localStorage.setItem("Authorization",value)
      state.Authorization = value
    },

    // 添加题目
    ADD_QUESTION(state,value){
      console.log("添加题目")
      value.id = nanoid()
      state.questionList.push(value)
    },

    // 更新题目
    UPDATE_QUESTION(state,value){
      console.log("修改题目")
      state.questionList = state.questionList.map(t => {
        return t.id === value.id
            ? value
            : t;
      });
    },

    // 删除题目
    DEL_QUESTION(state,index){
      state.questionList.splice(index,1)
    },

    // 添加选择题
    ADD_CHOICETYPE(state,value){
      state.choiceType.push(value)
      console.log(state.choiceType)
    },

    // 添加非选择题
    ADD_UNCHOICETYPE(state,value){
      state.unChoiceType.push(value)
    },

    // 删除一道选择题
    DELETE_CHOICETYPE(state,index){
      // 删除下标为index的一个元素
      state.choiceType.splice(index,1)
    },


  },
  actions: {
    addOrUpdate(context,value){
      if (value.id.length!=0){
        context.commit("UPDATE_QUESTION",value)
      }
      else {
        context.commit("ADD_QUESTION",value)
      }
    }
  },
  modules: {
  },
  getters:{
    totalScore(state){
      let totalScore=0
      state.questionList.forEach((q)=>{
          totalScore += q.score
      })
      return totalScore
    }
  }
})
