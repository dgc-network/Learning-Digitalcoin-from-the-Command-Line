# Chapter 15: Talking to Digitalcoind with C

While working with Digitalcoin Scripts, we hit the boundaries of what's possible with `digitalcoin-cli`: it can't currently be used to generate transactions containing unusual scripts. Shell scripts also aren't great for some things, such as creating listener programs that are constantly polling. Fortunately, there are other ways to access the Digitalcoin network: programming APIs.

This section focuses on three different libraries that can be used as the foundation of sophisticated C programming: an RPC library and a JSON library together allow you to recreate a lot of what you did in shell scripts, but now using C; while a ZMQ library links you in to notifications, something you haven't been able to previously access. (The next chapter will cover an even more sophisticated library called Libwally, to finish out this introductory look at programming Digitalcoin with C.)

## Objectives for This Chapter

After working through this chapter, a developer will be able to:

   * Create C Programs that use RPC to Talk to the Digitalcoind
   * Create C Programs that use ZMQ to Talk to the Digitalcoind
   
Supporting objectives include the ability to:

   * Understand how to use an RPC library
   * Understand how to use a JSON library
   * Understand the capabilities of ZMQ
   * Understand how to use a ZMQ library
   
## Table of Contents

  * [Section One: Accessing Digitalcoind in C with RPC Libraries](15_1_Accessing_Digitalcoind_with_C.md)
  * [Section Two: Programming Digitalcoind in C with RPC Libraries](15_2_Programming_Digitalcoind_with_C.md)
  * [Section Three: Receiving Notifications in C with ZMQ Libraries](15_3_Receiving_Digitalcoind_Notifications_with_C.md)
