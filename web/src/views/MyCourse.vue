<template>
    <div style="padding-left: 50px;padding-right: 50px;padding-top: 35px">
        <div>
            <a-button type="primary" @click="showModal">
                新建课程
            </a-button>
        </div>
        <div  style="margin-bottom: 18px;margin-top: 18px">
            <span style="color: black">我教的课</span>
        </div>
        <div >
            <a-row :gutter="[{ xs: 8, sm: 16, md: 24, lg: 32 }, { xs: 8, sm: 16, md: 24, lg: 32 }]">
                <a-col class="gutter-row" :span="6" v-for="course in courses" :key="course.id">

                        <a-card :hoverable="true" style="width: 300px;height: 220px" >
                            <span slot="extra" @click="showConfirm(course.id)" style="color: #3894FF">删除</span>
                            <router-link :to="{
                                            path:'/course_manage',
                                            query:{
                                                courseId:course.id
                                            }
                                        }">
                                <p style="text-align: center;font-weight: bold;font-size: 30px">{{course.name}}</p>
                                <p style="color: black">教师：{{course.teacher.name}}</p>
                                <span style="color: black">班级：</span>
                                <span style="color: black" v-for="c in course.classesList">{{c.name+" "}}</span>
                            </router-link>
                        </a-card>

                </a-col>
            </a-row>


        </div >
        <a-modal
                title="新建课程"
                :visible="visible"
                :confirm-loading="confirmLoading"
                @ok="handleOk"
                @cancel="handleCancel"
                okText="确定"
                cancelText="取消"
        >
            <!-- label-col标签占用空间，wrapper-col内容占用空间-->
            <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" @submit="handleSubmit">
                <a-form-item label="课程名称">
                    <a-input
                            v-decorator="[
                                    'courseName',
                                    { rules: [{ required: true, message: '请输入课程名称！' }] }
                                ]"
                            placeholder="请输入课程名称"
                    />
                </a-form-item>
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
                        <a-select-option v-for="c in classes" :key="c.id">
                            {{c.name}}
                        </a-select-option>
                    </a-select>
                </a-form-item>
            </a-form>
        </a-modal>
    </div>
</template>

<script>
    import axios from 'axios'
    import qs from 'qs'
    import {mapMutations, mapState} from "vuex";
    export default {
        name: "MyCourse",
        computed:{
            ...mapState(['userInfo'])
        },
        data(){
            return{
                classes:[],
                username:"",
                courses:[],
                visible: false,
                confirmLoading: false,
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        mounted(){
            this.load()
        },
        methods:{
            ...mapMutations(['SET_USERNAME']),
            // 加载页面数据
            load() {
                axios.get("/mycourse").then(res=>{
                    this.courses = res.data.data.courses
                    this.SET_USERNAME(res.data.data.userName)
                })
                axios.get("/allClasses").then(res=>{
                    if (res.data.code === "0"){
                        this.classes = res.data.data
                    }
                })
            },
            //删除课程确认框
            showConfirm(id){
                var that = this
                this.$confirm({
                    title: '提示',
                    content: '删除该课程后，所有作业记录将清除',
                    okText:'确定',
                    cancelText:'取消',
                    onOk() {
                        axios.delete("/mycourse",{
                            params:{
                                id
                            }
                        }).then(res=>{
                            if (res.data.code==="0") {
                                that.$message.success("删除课程成功")
                                that.load()
                            }
                            else {
                                that.$message.error("删除课程失败")
                            }
                        })
                    },
                    onCancel() {},
                });
            },
            //显示新建课程对话框
            showModal() {
                this.visible = true;
                this.form.resetFields()
            },
            handleOk(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        let data = qs.stringify({courseName:values.courseName,selectClass:values.selectClass},{arrayFormat:"brackets"})
                        console.log('Received values of form: ', data);
                        axios.post("/mycourse",data).then(res=>{
                            if (res.data.code === "0"){
                                this.$message.success("新建课程成功")
                                this.load()
                            }
                            else{
                                this.$message.error("新建课程失败")
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
            handleChange(value) {
                console.log(`selected ${value}`);
            },
        }
    }
</script>

<style scoped>
    .gutter-example >>> .ant-row > div {
        background: transparent;
        border: 0;
    }
    .gutter-box {
        background: #00a0e9;
        padding: 5px 0;
    }
</style>