<template>
    <div>
        <!--面包屑-->
        <div style="margin: 10px 30px">
            <a-breadcrumb>
                <a-breadcrumb-item>
                    <router-link :to="{
                            path:'/stu_course'
                        }">我学的课</router-link>
                </a-breadcrumb-item>
                <a-breadcrumb-item>我的作业</a-breadcrumb-item>
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
                      <a-button size="small" type="primary" @click="toDoHomework(record.id)">作答</a-button>
                    </span>
            </a-table>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapMutations, mapState} from "vuex";
    const queryData = params => {
        return axios.get('/homework/myHomework', { params: params });
    };
    export default {
        name: "MyHomework",
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
                        title: '发布时间',
                        dataIndex: 'classHomework.publishTime',
                        sorter:true
                    },
                    {
                        title: '提交时间',
                        dataIndex: 'answerSummary.submitTime',
                        sorter:true
                    },
                    {
                        title: '分数',
                        dataIndex: 'answerSummary.score',
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
            toDoHomework(homeworkId){
                this.$router.push({
                    path:'/doHomework',
                    query:{homeworkId: homeworkId,courseId:this.courseId}
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