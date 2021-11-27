<template>
    <div>
        <div style="height: 50px">
            <!--面包屑-->
            <div style="margin: 10px 30px;float:left;">
                <a-breadcrumb>
                    <a-breadcrumb-item>
                        <router-link :to="{
                            path:'/mycourse'
                        }">我教的课</router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item> <router-link :to="{
                            path:'/course_manage'
                        }">课程管理</router-link></a-breadcrumb-item>
                    <a-breadcrumb-item>
                        提交列表
                    </a-breadcrumb-item>

                </a-breadcrumb>
            </div>
            <div style="margin: 10px 30px;float: right">
                作业一
            </div>
        </div>
        <div style="margin-left: 30px;margin-right:30px;background-color: white;height: 580px;padding: 20px">
            <!--班级筛选-->
            <div>
                <span style="color: black">班级筛选</span>
                <a-select default-value="-1" style="width: 180px;margin-left: 10px" @change="handleChange">
                    <a-select-option value="-1">
                        全部班级
                    </a-select-option>
                    <a-select-option v-for="c in classes" :key="c.classId">
                        {{c.className}}
                    </a-select-option>
                </a-select>
            </div>
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
                        :data-source="data"
                        :pagination="pagination"
                        :rowKey="(record)=>{return record.no}"
                        @change="handleTableChange"
                >
                    <span slot="action" slot-scope="text,record">
                        <router-link :to="{
                            path:'/check_homework',
                            query:{
                                studentId:record.no,
                                homeworkId:$route.query.homeworkId
                            }
                        }">批阅</router-link>
                    </span>
                </a-table>
                <a-table
                        v-if="value==2"
                        :columns="columns2"
                        :data-source="data"
                        :pagination="pagination"
                        :rowKey="(record)=>{return record.no}"
                        @change="handleTableChange"
                >
                    <span slot="action" >
                        <router-link :to="{
                            path:'',
                        }">批阅</router-link>
                    </span>
                </a-table>
            </div>
        </div>



    </div>
</template>

<script>
    import axios from 'axios';

    const queryData = params => {
        return axios.get('https://randomuser.me/api', { params: params });
    };
    export default {
        name: "SubmitList",
        data(){
            return{
                pagination: {
                    pageSize:6
                },
                loading: false,
                data:[
                    {
                        name: 'John Brown',
                        no: '20192001',
                        class:"软工1903",
                        score:100,
                        time:"2021-11-25",
                    },
                    {
                        name: 'John Brown',
                        no: '20192002',
                        class:"软工1903",
                        score:56,
                        time:"2021-11-18",
                    },
                    {
                        name: 'John Brown',
                        no: '20192003',
                        class:"软工1902",
                        score:88,
                        time:"2021-11-20",
                    },

                ],
                columns1:[
                    {
                        title: '学号',
                        dataIndex: 'no',
                        key: 'no',
                        defaultSortOrder: 'ascend',
                        sorter: (a, b) => a.no - b.no,
                    },
                    {
                        title: '姓名',
                        dataIndex: 'name',
                        key: 'name',
                    },
                    {
                        title: '班级',
                        dataIndex: 'class',
                        key: 'class',
                    },
                    {
                        title: '成绩',
                        key: 'score',
                        dataIndex: 'score',
                        sorter: (a, b) => a.score - b.score,
                    },
                    {
                        title: '提交时间',
                        key: 'time',
                        dataIndex: 'time',
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
                        key: 'no',
                        defaultSortOrder: 'ascend',
                        sorter: (a, b) => a.no - b.no,
                    },
                    {
                        title: '姓名',
                        dataIndex: 'name',
                        key: 'name',
                    },
                    {
                        title: '班级',
                        dataIndex: 'class',
                        key: 'class',
                    },
                ],
                value:1,
                classes:[
                    {
                        classId:"001",
                        className:"A班"
                    },
                    {
                        classId: "002",
                        className:"B班"
                    }
                ],
            }
        },
        mounted() {
            this.fetch();
        },
        methods: {
            onChange(){

            },
            handleChange(){

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
        },
    }
</script>

<style scoped>

</style>