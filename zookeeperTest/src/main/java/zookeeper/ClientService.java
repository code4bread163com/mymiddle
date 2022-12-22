package zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangliang
 * @date 2019/10/29
 */
@Service
public class ClientService {

    private CuratorFramework zkClient;

    private String path = "/data";

    @PostConstruct
    public void testClientNew() {
        try {
            String zkServerAddress = "10.10.136.114:2181,10.10.136.114:2182,10.10.136.114:2183";
            ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(500000, 3, 50000000);
            zkClient = CuratorFrameworkFactory.builder()
                    .connectString(zkServerAddress)
                    .sessionTimeoutMs(60 * 1000)
                    .connectionTimeoutMs(15 * 1000)
                    .retryPolicy(retryPolicy)
                    .namespace("mynode")
                    .build();
            //zkClient = CuratorFrameworkFactory.newClient(zkServerAddress, retryPolicy);


            //很重要 一定要调用start来创建session链接
            zkClient.start();

            //zkClient.blockUntilConnected();

            //zkClient.close();

            System.out.println(zkClient.getState());

            initWatch();

            //ZKPaths.makePath()
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void testNodeCreate() {
        try {
            Stat stat = zkClient.checkExists().forPath(path);
            //zkClient.create().forPath("/mynode", "nodedata".getBytes());
            //递归创建节点,父节点一定为持久节点
            //zkClient.create().creatingParentsIfNeeded().forPath(path, "{\"version\"=0}".getBytes());
            //Object tt = zkClient.create().creatingParentsIfNeeded().forPath(path);
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            System.out.println("test");
        } catch (Exception e) {
            System.out.println("testNodeCreate error:" + e.toString());
        }
    }

    public void testNodeData() {
        try {
            byte[] bytes = zkClient.getData().forPath(path);
            System.out.println("data:" + new String(bytes, StandardCharsets.UTF_8));

            Stat stat = new Stat();
            byte[] byte1 = zkClient.getData().storingStatIn(stat).forPath(path);
            System.out.println(new String(byte1, StandardCharsets.UTF_8));
            System.out.println(JSON.toJSONString(stat));

            //zkClient.setData().forPath("/mynode/data", "{\"versio\":0}".getBytes());
            // cas
            Object c = zkClient.setData().withVersion(3).forPath(path, "{\"version\":1}".getBytes());
            System.out.println(JSON.toJSONString(c));
        } catch (Exception e) {
            System.out.println("testNodeData error:" + e.toString());

        }
    }

    public void testDeleteNode() {
        try {
            zkClient.delete().forPath(path);
            zkClient.delete().deletingChildrenIfNeeded().forPath(path);
            zkClient.delete().guaranteed().forPath(path);
        } catch (Exception e) {
            System.out.println("testDeleteNode error:" + e.toString());
        }
    }

    public void testOldWatch() {
        try {

            //设置监听器
            zkClient.getData().usingWatcher(new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("监听到节点事件:" + JSON.toJSONString(watchedEvent));
                }
            }).forPath(path);

            //第一次更新
            zkClient.setData().forPath(path, "1".getBytes());
            //第二次更新
            zkClient.setData().forPath(path, "2".getBytes());

            //但只触发一次，watch只能用一次

        } catch (Exception ex) {
            System.out.println("testOldWatch error:" + ex.toString());

        }
    }

    public void initWatch() {
        try {

            //监听本节点的变化  节点可以进行修改操作  删除节点后会再次创建(空节点)
            NodeCache nodeCache = new NodeCache(zkClient, path);
            //调用start方法开始监听
            nodeCache.start();
            //添加NodeCacheListener监听器
            nodeCache.getListenable().addListener(() -> {
                System.out.println("监听到事件nodechange变化，当前数据:" + new String(nodeCache.getCurrentData().getData())
                        + " path:" + nodeCache.getCurrentData().getPath()
                        + " stat:" + JSON.toJSONString(nodeCache.getCurrentData().getStat()));
            });


            PathChildrenCache childrenCache = new PathChildrenCache(zkClient, "/", true);
            PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    System.out.println("开始进行事件分析:-----");
                    ChildData data = event.getData();
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            System.out.println("CHILD_ADDED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;
                        case CHILD_REMOVED:
                            System.out.println("CHILD_REMOVED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;
                        case CHILD_UPDATED:
                            System.out.println("CHILD_UPDATED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;
                        default:
                            break;
                    }
                }
            };
            childrenCache.getListenable().addListener(childrenCacheListener);
            childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);


        } catch (Exception ex) {
            System.out.println("initWatch error:" + ex.toString());

        }
    }

    public void testNewWatch() {
        try {
            //第一次更新
            //zkClient.setData().forPath(path, "first update".getBytes());
            Thread.sleep(1000);
            //第二次更新
            zkClient.setData().forPath(path, "second update".getBytes());

        } catch (Exception ex) {
            System.out.println("testNewWatch error:" + ex.toString());
        }
    }

    @PreDestroy
    public void destory() {
        System.out.println("zkclient destory!!!");
        zkClient.close();
    }
}
