[TOC]

# RocketMQ

## Name Server



## Broker







# Redis

## Lock

In high-concurrency scenario, we need to **lock the resource** to make sure there is only **one user** access it at the time.

### Deadlock

There are situations we might encounter deadlocks:

1. Program throw exception: use try-finally clause
2. Program is down: set expiration time for the lock but need to make sure to set the lock and expiration date in the **atomic way**.

### Lock Expiration

There are also times the time required to complete a task exceeds the lock expiration time. Thus, we need to refresh lock expiration time if the program cannot finish before the expiration time.

<img src="./assets/Screenshot 2024-11-19 at 8.45.07 PM.png" alt="Screenshot 2024-11-19 at 8.45.07 PM" style="zoom:50%;" />

Redison provides a method to set the lock with expiration and put a timeout watch listener to ensure that it refreshes the expiration time while the program is running.





