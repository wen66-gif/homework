<template>
    <div>
        <div style="height: 50px">
            <!--面包屑-->
            <div style="margin: 10px 30px;float:left;">
                <a-breadcrumb>
                    <a-breadcrumb-item>
                        <router-link :to="{
                            path:'/tea_course'
                        }">我教的课</router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item> <router-link :to="{
                            path:'/course_manage',
                            query:{courseId:this.courseId,activeKey:2}
                        }">课程管理</router-link></a-breadcrumb-item>
                    <a-breadcrumb-item>
                        提交列表
                    </a-breadcrumb-item>

                </a-breadcrumb>
            </div>
            <div style="margin: 10px 30px;float: right">
                {{homeworkName}}
            </div>
        </div>
        <div style="margin-left: 30px;margin-right:30px;background-color: white;height: 580px;padding: 20px">

            <!--筛选已交/未交-->
            <div style="margin: 10px 0">
                <a-radio-group v-model="value" @change="onChange">
                    <a-radio :value="1">
                        已交
                    </a-radio>
                    <a-radio :value="2">
                        未交
                    </a-radio>
                </a-radio-group>
            </div>
            <!--表格-->
            <div>
                <a-table
                        v-if="value==1"
                        :columns="columns1"
                        :data-source="data1"
                        :pagination="pagination"
                        :rowKey="(record)=>{return record.id}"
                        @change="handleTableChange"
                >
                    <span slot="action" slot-scope="text,record">
                        <router-link :to="{
                            path:'/check_homework',
                            query:{
                                studentNo:record.no,
                                homeworkId:$route.query.homeworkId
                            }
                        }">批阅</router-link>
                    </span>
                </a-table>
                <a-table
                        v-if="value==2"
                        :columns="columns2"
                        :data-source="data2"
                        :pagination="pagination"
                        :rowKey="(record)=>{return record.no}"
                        @change="handleTableChange"
                >
                </a-table>
            </div>
        </div>



    </div>
</template>

<script>
    import axios from 'axios';
    import {mapMutations, mapState} from "vuex";

    const queryData = params => {
        return axios.get('/answerSummary/findList', { params});
    };
    export default {
        name: "SubmitList",
        data(){
            return{
                filters:[],
                homeworkName: '',
                pagination: {
                    pageNum:1,
                    pageSize:2
                },
                loading: false,
                data1:[],
                data2:[],
                columns1:[
                    {
                        title: '学号',
                        dataIndex: 'no',
                        defaultSortOrder: 'ascend',
                        sorter:true
                    },
                    {
                        title: '姓名',
                        dataIndex: 'student.name',
                    },
                    {
                        title: '班级',
                        dataIndex: 'classes.name',
                    },
                    {
                        title: '成绩',
                        dataIndex: 'score',
                        sorter:true
                    },
                    {
                        title: '提交时间',
                        dataIndex: 'submitTime',
                        sorter:true
                    },
                    {
                        title: '操作',
                        key: 'action',
                        scopedSlots: { customRender: 'action' },
                    },
                ],
                columns2:[
                    {
                        title: '学号',
                        dataIndex: 'no',
                        defaultSortOrder: 'ascend',
                        sorter:true
                    },
                    {
                        title: '姓名',
                        dataIndex: 'student.name',
                    },
                    {
                        title: '班级',
                        dataIndex: 'classes.name',
                    },
                ],
                value:1,
            }
        },
        computed:{
            ...mapState(['courseId'])
        },
        mounted() {
            this.SET_HOMEWORKID(this.$route.query.homeworkId)
            // 获取当前作业的所有班级，作业名
            axios.get("homework/oneHomework",{params:{homeworkId:this.$route.query.homeworkId}}).then(res=>{
                res.data.data.classesList.forEach((c)=>{
                this.filters.push({text:c.name,value:c.name})
                })
                // 表头筛选
                this.columns1 = this.columns1.map(c => {
                    return c.title === "班级"
                        ? {title: '班级', dataIndex: 'className',filters:this.filters}
                        : c;
                });
                this.columns2 = this.columns2.map(c => {
                    return c.title === "班级"
                        ? {title: '班级', dataIndex: 'className',filters:this.filters}
                        : c;
                });
                this.homeworkName = res.data.data.name
            })
            this.fetch(this.pagination);
        },
        methods: {
            ...mapMutations(['SET_HOMEWORKID']),
            // 筛选已交、未交
            onChange(e){
                if (e.target.value==1){
                    this.fetch({
                        value :1,
                        pageNum:1,
                        pageSize:2
                    });
                }
                else {
                    this.fetch({
                        value:2,
                        pageNum:1,
                        pageSize:2})
                }
            },
            handleTableChange(pagination, filters, sorter) {
                const pager = { ...this.pagination };
                pager.current = pagination.current;
                this.pagination = pager;
                this.fetch({// 根据pagination改变后的参数查询
                    pageSize: pagination.pageSize,   // 页面大小
                    pageNum: pagination.current,       // 单前页码
                    // homeworkId: this.$route.query.homeworkId,
                    sortField: sorter.field,
                    sortOrder: sorter.order,
                    value: this.value,
                    ...filters
                });
            },
            fetch(params = {}) {
                this.loading = true;
                queryData({
                    value: 1,
                    pageSize: 2,
                    homeworkId:this.$route.query.homeworkId,
                    ...params,
                }).then(({ data }) => {
                    const pagination = { ...this.pagination };
                    // 从后端获取数据总量total
                    pagination.total = data.data.total
                    this.loading = false;
                    // 将数据传给data
                    if (this.value==1)
                        this.data1 = data.data.records;
                    else
                        this.data2 = data.data.records;
                    this.pagination = pagination;
                });
            },
        },
    }
</script>

<style scoped>

</style>