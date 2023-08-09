<template>
  <div class="btn"><a-button type="primary" @click="showModal">新增</a-button></div>
  <a-table :data-source="passengers" :columns="columns" :pagination="pagination" @change="handleTableChange">
  </a-table>
  <a-modal v-model:visible="visible" title="乘车人" @ok="handleOk" ok-text="确认" cancel-text="取消">
    <a-form :model="passenger" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="姓名">
        <a-input v-model:value="passenger.name"/>
      </a-form-item>
      <a-form-item label="身份证">
        <a-input v-model:value="passenger.idCard" show-count :maxlength="18"/>
      </a-form-item>
      <a-form-item label="类型">
        <a-select v-model:value="passenger.type">
          <a-select-option value="1">成人</a-select-option>
          <a-select-option value="2">儿童</a-select-option>
          <a-select-option value="3">学生</a-select-option>
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
    const visible = ref(false)
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
    ]
    const passenger = reactive({
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
      pageSize: 2
    })

    const handleQuery = (param) => {
      axios.get('/member/passenger/query-list', {
        params: {
          page: param.page,
          size: param.size
        }
      }).then(res => {
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
    const showModal = () => {
      visible.value = true
    }
    const handleOk = e => {
      console.log(e)
      console.log(passenger)
      axios.post("/member/passenger/save", passenger).then(res => {
        let data = res.data
        if (data.success) {
          // 保存成功
          notification.success({description: "乘车人添加成功"})
          visible.value = false
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
        size: 2
      })
    })
    return {
      visible,
      showModal,
      handleOk,
      passenger,
      columns,
      passengers,
      pagination,
      handleTableChange
    };
  },
});
</script>
<style scoped>
.btn {
  margin-bottom: 20px;
}
</style>
