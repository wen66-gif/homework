<template>
        <div>
            <!--面包屑-->
            <div style="margin: 10px 30px">
                <a-breadcrumb>
                    <a-breadcrumb-item>
                        <router-link :to="{
                            path:'/mycourse'
                        }">我教的课</router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item>课程管理</a-breadcrumb-item>
                </a-breadcrumb>
            </div>
            <div style="width: 95%;height: 85%;margin-left:30px;margin-right:30px;background-color: white;">
                <div style="padding-left: 20px">
                    <a-tabs v-model="activeKey" size="large" :animated="false" :tabBarGutter=100>
                        <a-tab-pane key="1" tab="班级管理">
                            <a-button type="primary" @click="addClass">
                                导入班级
                            </a-button>
                            <p style="color: black;margin: 10px">班级列表</p>
                            <div style="height: 421px; overflow:auto;margin-top: 10px;">
                                <div v-for="c in selectedClasses" :key="c.id" style="border:2px solid #eee;height: 40px;width: 80%;padding-left: 10px;line-height: 40px;margin-top: 10px">
                                    <span style="color: black;">{{c.name}}</span>
                                    <a-button type="danger" size="small" style="float: right;margin-right: 15px;margin-top: 5px" @click="handleDeleteClass(c.id)">删除</a-button>
                                </div>
                            </div>

                            <a-modal
                                    title="导入班级"
                                    :visible="visible"
                                    :confirm-loading="confirmLoading"
                                    @ok="handleOk"
                                    @cancel="handleCancel"
                                    okText="确定"
                                    cancelText="取消"
                            >
                                <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" @submit="handleSubmit">
                                    <a-form-item label="班级">
                                        <a-select
                                                v-decorator="[
                                            'selectClass',
                                            { rules: [{ required: true, message: '请选择班级！' }] }
                                        ]"
                                                mode="multiple"
                                                style="width: 100%"
                                                placeholder="请选择班级"
                                                @change="handleChange"
                                        >
                                            <a-select-option v-for="c in unselectClasses" :key="c.id">
                                                {{c.name}}
                                            </a-select-option>
                                        </a-select>
                                    </a-form-item>
                                </a-form>
                            </a-modal>
                        </a-tab-pane>
                        <a-tab-pane key="2" tab="作业管理">
                            <!-- 功能按钮-->
                            <div>
                                <a-button type="primary" @click="toEditHomework">
                                    新建作业
                                </a-button>
                                <a-button type="primary" @click="toHomeworkRep($route.query.courseId)" style="margin-left: 30px">
                                    作业库
                                </a-button>
                            </div>
                            <!--班级筛选-->
                            <div style="margin-top: 20px">
                                <span style="color: black;margin: 10px">作业列表</span>
                                <a-select :default-value="-1" style="width: 180px;margin-left: 10px" @change="handleChange">
                                    <a-select-option :value="-1">
                                        全部班级
                                    </a-select-option>
                                    <a-select-option v-for="c in selectedClasses" :key="c.id">
                                        {{c.name}}
                                    </a-select-option>
                                </a-select>
                            </div>
                            <div style="height: 400px; overflow:auto;margin-top: 10px;">
                                <div v-for="h in homework" :key="h.id" style="border:2px solid #eee;height: 60px;width: 80%;padding: 5px;margin-top: 10px">
                                    <div style="float: left">
                                        <div>
                                            <span style="color: black;font-size: 18px;">{{h.name}}</span>
                                        </div>
                                        <div>
                                            <span style="width: 10%;" v-for="c in h.classesList">{{c.name+" "}}</span>
                                        </div>
                                    </div>
                                    <a-button type="danger"  @click="showConfirm(h.id)" style="float: right ;margin-top: 10px">删除</a-button>
                                    <a-button type="primary"  @click="toSubmitList(h.id)" style="float: right ;margin-top: 10px;margin-right: 30px">查看</a-button>
                                </div>
                            </div>
                        </a-tab-pane>
                    </a-tabs>
                </div>
            </div>

        </div>




</template>

<script>
    import qs from "qs";
    import axios from "axios";
    import {mapMutations, mapState} from "vuex";

    export default {
        name: "ClassManage",
        data() {
            return {
                activeKey:"1",
                // 筛选作业
                homework:[],
                // 所有作业
                allHomework:[],
                // 已经选上的班级
                selectedClasses:[],
                // 未选上的班级
                unselectClasses:[],

                visible: false,
                confirmLoading: false,
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),

            };
        },
        mounted() {
            this.SET_COURSEID(this.$route.query.courseId)
            this.load(this.$route.query.courseId)
            if (this.$route.query.activeKey) {
                this.activeKey = this.$route.query.activeKey
                console.log("面板：",this.activeKey)
            }
        },
        methods:{
            ...mapMutations(['SET_COURSEID']),
            load(courseId){
                // 获取已选择的班级
              axios.get("/selectedClasses",{
                  params:{
                      courseId
                  }
              }).then(res=>{
                  if (res.data.code==="0"){
                      // filter过滤null元素
                      this.selectedClasses = res.data.data.filter(n => n)
                  }
              })
                // 获取未选上的班级
                axios.get("/unselectClasses",{params:{courseId}}).then(res=>{
                    if (res.data.code==="0"){
                        this.unselectClasses = res.data.data
                    }
                })
                axios.get("/homework/allPublishHomework",{params:{courseId}}).then(res=>{
                    if (res.data.code==="0"){
                        this.homework = this.allHomework = res.data.data

                    }
                })
            },
            toHomeworkRep(courseId){
              this.$router.push({
                  path:'/homeworkrep',
                  query:{
                      courseId
                  }
              })
            },
            toSubmitList(homeworkId){
                this.$router.push({
                    path:'/submit_list',
                    query:{
                        homeworkId
                    }
                })
            },
            toEditHomework(){
                this.$router.push({
                    path:'/edit_homework',
                    query:{
                        courseId:this.$route.query.courseId
                    }
                })
            },
            //显示导入班级对话框
            addClass() {
                this.visible = true;
                this.form.resetFields()
            },
            handleOk(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        let data = qs.stringify({selectClass:values.selectClass,courseId:this.$route.query.courseId},{arrayFormat:"brackets"})
                        console.log(data)
                        axios.post("/import_class",data).then(res=>{
                            if (res.data.code === "0"){
                                this.$message.success("导入班级成功")
                                this.load(this.$route.query.courseId)
                            }
                            else{
                                this.$message.error("导入班级失败")
                            }
                        })
                        this.confirmLoading = true;
                        this.visible = false;
                        this.confirmLoading = false;
                    }
                });

            },
            handleCancel(e) {
                this.visible = false;
            },
            handleSubmit(e) {

            },
            // 筛选班级
            handleChange(value) {
                this.homework = []
                var allHomework = this.allHomework
                if (value == -1){
                    this.homework = allHomework
                }
                else {
                    for (let i = 0; i < allHomework.length; i++){
                        allHomework[i].classesList.forEach((classes)=>{
                            if (classes.id == value){
                                this.homework.push(allHomework[i])
                                return
                            }
                        })
                    }
                }
            },
            handleDeleteClass(id){
                var that = this
                this.$confirm({
                    title: '提示',
                    content: '删除该班级后，学生作答记录将永久删除',
                    okText:'确定',
                    cancelText:'取消',
                    onOk() {
                        axios.delete("/delete_class",{
                            params:{
                                classId:id,
                                courseId:that.$route.query.courseId
                            }
                        }).then(res=>{
                            if (res.data.code==="0") {
                                that.$message.success("删除班级成功")
                                that.load(that.$route.query.courseId)
                            }
                            else {
                                that.$message.error("删除班级失败")
                            }
                        })
                    },
                    onCancel() {},
                });
            },
            //删除作业确认框
            showConfirm(homeworkId){
                var that = this
                this.$confirm({
                    title: '提示',
                    content: '删除该作业后，学生作答记录将永久删除',
                    okText:'确定',
                    cancelText:'取消',
                    onOk() {
                        axios.delete("/delete_homework_record",{
                            params:{
                                homeworkId
                            }
                        }).then(res=>{
                            if (res.data.code==="0") {
                                that.$message.success("删除作业成功")
                                that.load(that.$route.query.courseId)
                            }
                            else {
                                that.$message.error("删除作业失败")
                            }
                        })
                    },
                    onCancel() {},
                });
            },
        }
    }
</script>

<style scoped>

</style>