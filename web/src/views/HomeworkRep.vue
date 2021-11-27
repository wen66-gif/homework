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
                            path:'/course_manage'
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
    const queryData = params => {
        return axios.get('https://randomuser.me/api', { params: params });
    };
    export default {
        name: "HomeworkRep",
        data(){
            return{
                loading: false,
                pagination:{
                    pageSize:8
                },
                columns:[
                    {
                        title: '作业名称',
                        dataIndex: 'name',
                        key: 'name',
                    },
                    {
                        title: '创建时间',
                        dataIndex: 'time',
                        key: 'time',
                    },
                    {
                        title: '操作',
                        key: 'action',
                        scopedSlots: { customRender: 'action' },
                    },
                ],
                // 获取到的作业数据
                data:[
                    {
                        id: '001',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '002',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '003',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '004',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '005',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '006',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '007',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '008',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '009',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                    {
                        id: '010',
                        name: '作业一',
                        time:"2021-11-25",
                    },
                ],
                // 发布作业可选班级，不包含已发布的班级
                classes:[
                    {
                        id:"1",
                        name:"软工1903"
                    },
                    {
                        id:"2",
                        name:"软工1901"
                    }
                ],
                visible: false,
                confirmLoading: false,
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        mounted() {
            this.fetch();
        },
        methods:{
            showConfirm(homeworkId){
                var that = this
                this.$confirm({
                    title: '提示',
                    content: '确定从作业库删除该作业吗？',
                    okText:'确定',
                    cancelText:'取消',
                    onOk() {
                        axios.delete("/teacher/delete_homework",{
                            params:{
                                homeworkId
                            }
                        }).then(res=>{
                            if (res.data.code==="0") {
                                that.$message.success("删除作业成功")
                                that.load()
                            }
                            else {
                                that.$message.error("删除作业失败")
                            }
                        })
                    },
                    onCancel() {},
                });
            },
            toEditHomework(homeworkId){
                this.$router.push({
                    path:'/edit_homework',
                    query:{
                        homeworkId
                    }
                })
            },
            handleTableChange(pagination, filters, sorter) {
                console.log(pagination);
                const pager = { ...this.pagination };
                pager.current = pagination.current;
                this.pagination = pager;
                this.fetch({// 根据pagination改变后的参数查询
                    results: pagination.pageSize,   // 页面大小
                    page: pagination.current,       // 单前页码
                });
            },
            fetch(params = {}) {
                this.loading = true;
                queryData({
                    results: 6,
                    ...params,
                }).then(({ data }) => {
                    const pagination = { ...this.pagination };
                    // 从后端获取数据总量total
                    // pagination.total = data.totalCount
                    pagination.total = 100;
                    this.loading = false;
                    // 将数据传给data
                    // this.data = data.results;
                    this.pagination = pagination;
                });
            },
            //
            publish(homeworkId){
                this.visible = true;
                this.form.resetFields()
            },
            handleOk(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        let data = qs.stringify({classes:values.classes},{arrayFormat:"brackets"})
                        console.log('Received values of form: ', data);
                        /*axios.post().then(res=>{
                            if (res.data.code === "0"){
                                this.$message.success("导入班级成功")
                                this.load()
                            }
                            else{
                                this.$message.error("导入班级失败")
                            }
                        })*/
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