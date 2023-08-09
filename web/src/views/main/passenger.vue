<template>
  <div class="btn"><a-button type="primary" @click="showModal">新增</a-button></div>
  <a-table :data-source="dataSource" :columns="columns">
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
import {defineComponent, reactive, ref} from 'vue';
import axios from 'axios'
import {notification} from "ant-design-vue";

export default defineComponent({
  setup() {
    const visible = ref(false)
    const dataSource = [
      {
        key: '1',
        name: '胡彦斌',
        age: 32,
        address: '西湖区湖底公园1号',
      },
      {
        key: '2',
        name: '胡彦祖',
        age: 42,
        address: '西湖区湖底公园1号',
      },
    ]

    const columns = [
      {
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '年龄',
        dataIndex: 'age',
        key: 'age',
      },
      {
        title: '住址',
        dataIndex: 'address',
        key: 'address',
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
    return {
      visible,
      showModal,
      handleOk,
      passenger,
      columns,
      dataSource
    };
  },
});
</script>
<style scoped>
.btn {
  margin-bottom: 20px;
}
</style>
