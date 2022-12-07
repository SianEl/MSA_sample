<template>
  <div>
    <p>
      {{ counter }}
    </p>
    <p>
      {{ message }}
    </p>
    <button @click="increment">Click!</button>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  props: {
    title: String,
  },
  setup(props, context) {
    console.log(props.title); // undefined
    console.log(props); //Proxy {title:undefined}

    console.log(context.attrs);
    console.log(context.emit);
    console.log(context.expose);
    console.log(context.slots);

    const counter = ref(0);
    const message = ref('Hello Vue3');
    const increment = () => {
      counter.value++;
    };
    return {
      counter,
      message,
      increment,
    };
  },
};
</script>

<style lang="scss" scoped></style>
