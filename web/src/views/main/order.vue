<template>
  <div class="order-train">
    <span class="order-train-main">{{dailyTrainTicket.date}}</span>&nbsp;
    <span class="order-train-main">{{dailyTrainTicket.trainCode}}</span>次&nbsp;
    <span class="order-train-main">{{dailyTrainTicket.start}}</span>站
    <span class="order-train-main">({{dailyTrainTicket.startTime}})</span>&nbsp;
    <span class="order-train-main">——</span>&nbsp;
    <span class="order-train-main">{{dailyTrainTicket.end}}</span>站
    <span class="order-train-main">({{dailyTrainTicket.endTime}})</span>&nbsp;

    <div class="order-train-ticket">
      <span v-for="item in seatTypes" :key="item.type">
        <span>{{item.desc}}</span>：
        <span class="order-train-ticket-main">{{item.price}}￥</span>&nbsp;
        <span class="order-train-ticket-main">{{item.count}}</span>&nbsp;张票&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </span>
    </div>

    <a-divider></a-divider>
    <b>勾选要购票的乘客：</b>&nbsp;
    <a-checkbox-group v-model:value="passengerChecks" :options="passengerOptions" /><br/>
    选中的乘客：{{passengerChecks}}
  </div>
</template>

<script>

import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "order-view",
  setup() {
    const passengers = ref([])
    const passengerOptions = ref([])
    const passengerChecks = ref([])
    const dailyTrainTicket = SessionStorage.get(SESSION_ORDER) || {};
    console.log("下单的车次信息", dailyTrainTicket);

    const SEAT_TYPE = window.SEAT_TYPE
    console.log(SEAT_TYPE)
    const seatTypes = []
    for(let KEY in SEAT_TYPE) {
      let key = KEY.toLowerCase()
      if(dailyTrainTicket[key] >= 0) {
        seatTypes.push({
          type: KEY,
          code: SEAT_TYPE[KEY]['code'],
          desc: SEAT_TYPE[KEY]['desc'],
          count: dailyTrainTicket[key],
          price: dailyTrainTicket[key + 'Price']
        })
      }
    }
    console.log("本车次提供的座位：", seatTypes)

    const handleQueryPassenger = () => {
      axios.get('/member/passenger/query-mine').then(res => {
        if(res.data.success) {
          passengers.value = res.data.content
          passengers.value.forEach(item => passengerOptions.value.push({
            label: item.name,
            value: item.id
          }))
        } else {
          notification.error({description: res.data.message})
        }
      })
    }

    onMounted(() => {
      handleQueryPassenger()
    })

    return {
      dailyTrainTicket,
      seatTypes,
      passengers,
      passengerOptions,
      passengerChecks
    };
  },
});
</script>

<style>
.order-train .order-train-main {
  font-size: 18px;
  font-weight: bold;
}
.order-train .order-train-ticket {
  margin-top: 15px;
}
.order-train .order-train-ticket .order-train-ticket-main {
  color: red;
  font-size: 18px;
}

</style>
