[TOC]

# RocketMQ

## Name Server

- **Broker Management**: The Name Server registers brokers and monitors their status, ensuring that producers and consumers have up-to-date information about broker availability. (Broker sends heart beats to name server every 30 seconds, name server checks heart beat data every 10 seconds. If a broker fails to send heartbeat in 120 seconds, it will get de-registered )
- **Routing Management**: It maintains a complete routing/topic table, allowing users to determine the appropriate broker for message storage or retrieval.
- **High avalibility**: **Nameserver Peer Mode** refers to a configuration setup in which multiple nameservers operate in a peer-to-peer manner, enabling high availability and fault tolerance.

## Broker

- **High Availability**: Use master-slave mode with automatic failover to ensure fault tolerance. (Master broker has brokerID=0, slaves have brokerID > 0)
- **Message Storage**: Mesages are read and write through disks

## Topic

- **Topic-Broker Link**: when a topic is created, there are 4 write-queues and 4 read queues created and connected to the disk location of different brokers in the cluster.



## Producer

- **Fetch topic table**: Producer fetches topic and queue table from name server every 30 seconds

- **Load Balanced Messages**: By default, producer sends messages to queue with load balancing approach. 

-  **Orderly Messages**, To ensure a messages group processed orderly, need to send them into the same queue. We can use hash method to send messages to the same queue.

- **Commit Log **:

  

## Queue

- 



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





