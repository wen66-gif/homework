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
                <a-breadcrumb-item>
                    <router-link :to="{
                        path:'/course_manage',
                        query:{courseId:this.courseId,activeKey:2}
                        }">课程管理</router-link>
                </a-breadcrumb-item>
                <a-breadcrumb-item>作业库</a-breadcrumb-item>
            </a-breadcrumb>
        </div>
        <div id="main_content">
            <a-table
                    :columns="columns"
                    :data-source="data"
                    :pagination="pagination"
                    :rowKey="(record)=>{return record.id}"
                    @change="handleTableChange"
            >
                    <span slot="action" slot-scope="text, record">
                      <a-button size="small" type="primary" @click="publish(record.id)">发布</a-button>
                      <a-divider type="vertical" />
                        <a-button size="small" type="primary" @click="toEditHomework(record.id)">编辑</a-button>
                      <a-divider type="vertical" />
                      <a-button size="small" type="danger" @click="showConfirm(record.id)">删除</a-button>
                    </span>
            </a-table>
            <!--选择班级-->
            <a-modal
                    title="发布作业"
                    :visible="visible"
                    :confirm-loading="confirmLoading"
                    @ok="handleOk"
                    @cancel="handleCancel"
                    okText="发布"
                    cancelText="取消"
                    :ok-button-props="{ props: { homeworkId: this.homeworkId} }"
            >
                <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
                    <a-form-item label="选择班级">
                        <a-select
                                v-decorator="[
                                            'classes',
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
    </div>
</template>

<script>
    import qs from "qs";
    import axios from "axios";
    import {mapMutations, mapState} from "vuex";
    const queryData = params => {
        return axios.get('homework/allHomework', { params: params });
    };
    export default {
        name: "HomeworkRep",
        data(){
            return{
                // 当前操作的作业id
                homeworkId:'',
                loading: false,
                pagination:{
                    pageNum:1,
                    pageSize:8
                },
                columns:[
                    {
                        title: '作业名称',
                        dataIndex: 'name',
                    },
                    {
                        title: '创建时间',
                        dataIndex: 'createTime',
                        sorter:true
                    },
                    {
                        title: '更新时间',
                        dataIndex: 'updateTime',
                        sorter:true
                    },
                    {
                        title: '操作',
                        key: 'action',
                        scopedSlots: { customRender: 'action' },
                    },
                ],
                // 获取到的作业数据
                data:[],
                // 发布作业可选班级，不包含已发布的班级
                classes:[],
                visible: false,
                confirmLoading: false,
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        mounted() {
            this.fetch(this.pagination);
            this.SET_COURSEID(this.$route.query.courseId)
        },
        computed:{
            ...mapState(['courseId'])
        },
        methods:{
            ...mapMutations(['SET_COURSEID']),
            // 删除作业
            showConfirm(homeworkId){
                var that = this
                this.$confirm({
                    title: '提示',
                    content: '删除该作业后，学生作答记录将永久删除',
                    okText:'确定',
                    cancelText:'取消',
                    onOk() {
                        axios.delete("/homework/deleteHomework",{
                            params:{
                                homeworkId
                            }
                        }).then(res=>{
                            if (res.data.code==="0") {
                                that.$message.success("删除作业成功")
                                that.fetch(that.pagination)
                            }
                            else {
                                that.$message.error("删除作业失败")
                            }
                        })
                    },
                    onCancel() {},
                });
            },
            // 编辑作业
            toEditHomework(homeworkId){
                this.$router.push({
                    path:'/edit_homework',
                    query:{
                        homeworkId,
                        courseId:this.$route.query.courseId
                    }
                })
            },
            handleTableChange(pagination, filters, sorter) {
                console.log(pagination);
                const pager = { ...this.pagination };
                pager.current = pagination.current;
                this.pagination = pager;
                this.fetch({// 根据pagination改变后的参数查询
                    pageSize: pagination.pageSize,   // 页面大小
                    pageNum: pagination.current,       // 单前页码
                    sortField: sorter.field,
                    sortOrder: sorter.order,
                });
            },
            fetch(params = {}) {
                this.loading = true;
                queryData({
                    pageSize: 8,
                    courseId:this.$route.query.courseId,
                    ...params,
                }).then(({ data }) => {
                    const pagination = { ...this.pagination };
                    // 从后端获取数据总量total
                    pagination.total = data.data.total
                    this.loading = false;
                    // 将数据传给data
                    this.data = data.data.records;
                    this.pagination = pagination;
                });
            },
            //
            publish(homeworkId){
                axios.get("/unPublishClasses",{
                    params:{
                        courseId:this.$route.query.courseId,
                        homeworkId:homeworkId
                    }
                }).then(res=>{
                    this.classes = res.data.data
                })
                this.homeworkId = homeworkId
                this.visible = true;
                this.form.resetFields()
            },
            handleOk(e) {
                console.log(e)
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        let data = qs.stringify({classes:values.classes,homeworkId:this.homeworkId},{arrayFormat:"brackets"})
                        axios.post("/classHomework/saveRecord",data).then(res=>{
                            if (res.data.code === "0"){
                                this.$message.success("发布作业成功")
                                this.fetch(this.pagination);
                            }
                            else{
                                this.$message.error("发布作业失败")
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
    #main_content {
        padding-left: 20px;
        width: 95%;
        height: 580px;
        margin-left:30px;
        margin-right:30px;
        background-color: white;
    }
</style>