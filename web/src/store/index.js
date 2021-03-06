import Vue from 'vue'
import Vuex from 'vuex'
import {nanoid} from "nanoid";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 记录点击的课程id
    courseId:'',
    // 记录点击的作业id
    homeworkId:'',
    // 题目列表
    questionList:[],
    // 非选择题
    unChoiceType:[],
    // 选择题
    choiceType:[],
    // 学生答案
    studentAnswer:[],
    // 当前用户名
    username:sessionStorage.getItem("username"),
    // 存储token
    Authorization:localStorage.getItem("Authorization") ? localStorage.getItem("Authorization") : '',
  },
  mutations: {
    // courseId改变
    SET_COURSEID(state,value){
      state.courseId = value
    },
    // homeworkId改变
    SET_HOMEWORKID(state,value){
      state.homeworkId = value
    },
    // 保存当前用户名
    SET_USERNAME(state,username){
      sessionStorage.setItem("username",username)
      state.username = sessionStorage.getItem("username")
    },

    // 清除用户名信息
    CLEAR_USERNAME(state){
      sessionStorage.clear()
      state.username = ''
    },

    // 保存token
    SET_TOKEN(state,value){
      localStorage.setItem("Authorization",value)
      state.Authorization = value
    },

    // 清除token
    CLEAR_TOKEN(state){
      localStorage.clear()
      state.Authorization = ''
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

    // 加载题目，修改作业时调用
    LOAD_QUESTION(state,value){
      state.questionList = value
      for(let i = 0; i < state.questionList.length; i++){
        delete state.questionList[i].no
      }
    },

    // 添加选择题
    ADD_CHOICETYPE(state,value){
      state.choiceType.push(value)
    },

    // 添加非选择题
    ADD_UNCHOICETYPE(state,value){
      state.unChoiceType.push(value)
    },

    // 删除一道选择题
    CLEARALLQUESTION(state){
      // 删除下标为index的一个元素
      state.choiceType = []
      state.unChoiceType = []
      state.questionList = []
    },

    // 添加学生答案
    ADD_STUDENTANSWER(state,value){
      state.studentAnswer.push(value)
    },

    // 修改学生答案
    UPDATE_STUDENTANSWER(state,value){
      state.studentAnswer = state.studentAnswer.map(answer => {
        return (answer.choiceId === value.choiceId && value.choiceId) || (answer.unchoiceId === value.unchoiceId && value.unchoiceId)
            ? value
            : answer;
      });
    },

    //清除缓存的学生答案
    CLEAR_STUDENTANSWER(state){
      state.studentAnswer.length = 0
    }


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
