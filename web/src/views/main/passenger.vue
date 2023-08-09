<template>
  <div class="btn">
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </div>
  <a-table :data-source="passengers" :columns="columns" :pagination="pagination" @change="handleTableChange" :loading="loading">
    <template #bodyCell="{column, record}">
      <template v-if="column.dataIndex === 'operation'">
        <a-space>
          <a @click="onEdit(record)">编辑</a>
          <a-popconfirm
            title="删除后不可恢复，确认删除?"
            @confirm="onDelete(record)"
            ok-text="确认"
            cancel-text="取消">
            <a style="color: red">删除</a>
          </a-popconfirm>
        </a-space>
      </template>
      <template v-else-if="column.dataIndex === 'type'">
        <span v-for="item in PASSENGER_TYPE_ARRAY" :key="item.key">
          <span v-if="item.key === record.type">
            {{item.value}}
          </span>
        </span>
      </template>
    </template>
  </a-table>
  <a-modal v-model:open="visible" title="乘车人" @ok="handleOk" ok-text="确认" cancel-text="取消">
    <a-form :model="passenger" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="姓名">
        <a-input v-model:value="passenger.name"/>
      </a-form-item>
      <a-form-item label="身份证">
        <a-input v-model:value="passenger.idCard" show-count :maxlength="18"/>
      </a-form-item>
      <a-form-item label="类型">
        <a-select v-model:value="passenger.type">
          <a-select-option v-for="item in PASSENGER_TYPE_ARRAY" :key="item.key" :value="item.key">{{item.value}}</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import {defineComponent, onMounted, reactive, ref} from 'vue';
import axios from 'axios'
import {notification} from "ant-design-vue";

export default defineComponent({
  setup() {
    const PASSENGER_TYPE_ARRAY = window.PASSENGER_TYPE_ARRAY;
    const visible = ref(false)
    const loading = ref(false)
    const columns = [
      {
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '身份证',
        dataIndex: 'idCard',
        key: 'idCard',
      },
      {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
      },
      {
        title: '操作',
        dataIndex: 'operation'
      }
    ]
    const passenger = ref({
      id: undefined,
      memberId: undefined,
      name: undefined,
      idCard: undefined,
      type: undefined,
      createTime: undefined,
      updateTime: undefined
    })
    const passengers = ref([])
    const pagination = reactive({
      total: 0,
      current: 1,
      pageSize: 5
    })

    const handleQuery = (param) => {
      if (!param) {
        param = {
          page: 1,
          size: pagination.pageSize
        }
      }
      loading.value = true
      axios.get('/member/passenger/query-list', {
        params: {
          page: param.page,
          size: param.size
        }
      }).then(res => {
        loading.value = false
        let data = res.data
        if(data.success) {
          passengers.value = data.content.list
          pagination.total = data.content.total
          pagination.current = param.page
        } else {
          notification.error({description: data.message})
        }
      })
    }
    const onAdd = () => {
      passenger.value = {}
      visible.value = true
    }
    const onEdit = (record) => {
      passenger.value = window.Tool.copy(record)
      visible.value = true
    }
    const onDelete = (record) => {
      console.log(record.id)
      axios.delete(`/member/passenger/delete/${record.id}`).then(res => {
        const data = res.data
        if(data.success) {
          notification.success({description: "删除成功"})
          handleQuery({
            page: pagination.current,
            size: pagination.pageSize
          })
        } else {
          notification.error({description: data.message})
        }
      })
    }
    const handleOk = e => {
      console.log(e)
      console.log(passenger)
      axios.post("/member/passenger/save", passenger.value).then(res => {
        let data = res.data
        if (data.success) {
          // 保存成功
          if (passenger.value.id) {
            notification.success({description: "乘车人修改成功"})
          } else {
            notification.success({description: "乘车人添加成功"})
          }
          visible.value = false
          handleQuery({
            page: pagination.current,
            size: pagination.pageSize
          })
        } else {
          // 保存失败
          notification.error({description: data.message})
        }
      })
    }
    const handleTableChange = (pagination) => {
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      })
    }
    onMounted(() => {
      handleQuery({
        page: 1,
        size: 5
      })
    })
    return {
      visible,
      onAdd,
      onEdit,
      handleOk,
      passenger,
      columns,
      passengers,
      pagination,
      handleTableChange,
      handleQuery,
      loading,
      onDelete,
      PASSENGER_TYPE_ARRAY
    };
  },
});
</script>
<style scoped>
.btn {
  margin-bottom: 20px;
}
</style>
