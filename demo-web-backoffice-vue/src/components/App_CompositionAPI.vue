<template>
  <div>
    <!-- 반응형 메세지는 버튼 클릭 시 !문자가 추가된다-->
    <h2>반응형 메세지</h2>
    <p>{{ reactiveMessage }}</p>
    <button type="button" v-on:click="addReactiveMessgae">Add Message</button>

    <!-- 기본 메세지는 버튼 클릭 시 !문자가 추가안된다-->
    <h2>기본 메세지</h2>
    <p>{{ normalMessage }}</p>
    <button type="button" v-on:click="addNormalMessage">Add Message</button>
  </div>
</template>

<script>
import { isRef, onBeforeMount, onMounted, ref } from 'vue';
export default {
  setup() {
    console.log('setup');
    const reactiveMessage = ref('Hello Reactive Message');
    const addReactiveMessgae = () => {
      reactiveMessage.value = reactiveMessage.value + '!';
    };
    console.log('isRef(reactiveMessage):' + isRef(reactiveMessage)); //반응형 변수인지 아닌지 판단하는 함수 true이면 ref변수이고 false이면 기본 변수

    let normalMessage = 'Hello Normal Message';
    const addNormalMessage = () => {
      normalMessage = normalMessage + '!';
    };
    console.log('isRef(normalMessage):' + isRef(normalMessage));

    onMounted(() => {
      console.log('onMounted');
    });
    onBeforeMount(() => {
      console.log('onBeforeMount');
    });

    return {
      reactiveMessage,
      normalMessage,
      addReactiveMessgae,
      addNormalMessage,
    };
  },
};
</script>

<style lang="scss" scoped></style>
