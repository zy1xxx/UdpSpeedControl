# UdpClientThread类（控制发送速率）

## 原理

通过控制数据包之间间隔时间控制发送速率

## 参数

```java
public UdpClientThread(String address, int port, String filePath ,double speed) {
    this.mAddress = address;
    this.port = port;
    this.filePath = filePath;
    this.speed=speed;
}
```

| 参数     | 说明                 |
| -------- | -------------------- |
| address  | 要发送目标的ip地址   |
| port     | 端口                 |
| filePath | 文件路径             |
| speed    | 发送速率（单位kb/s） |

## 用法

```java
UdpClientThread mThread = new UdpClientThread(mAddress, port,filePath,speed);
mThread.start();
```

## 优化

由于是开环控制，不能保证绝对的速率，在发送包的时间抖动下可能会造成较大误差，需要进一步优化。