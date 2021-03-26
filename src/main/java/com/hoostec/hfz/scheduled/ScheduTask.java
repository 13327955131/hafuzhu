package com.hoostec.hfz.scheduled;

import com.hoostec.hfz.entity.HfzPayDay;
import com.hoostec.hfz.entity.HfzProfitPickRecord;
import com.hoostec.hfz.service.HfzPayDayService;
import com.hoostec.hfz.service.HfzProfitPickRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 *
 * @author loo
 */
@Component
@Slf4j
public class ScheduTask {
    @Autowired
    private HfzPayDayService hfzPayDayService;

    @Autowired
    private HfzProfitPickRecordService hfzProfitPickRecordService;

    /**
     * 定时任务启动
     */
    @Scheduled(cron = "0 0 0 * * ?")  //每天凌晨
   // @Scheduled(cron = "0 */1 * * * ?")        //1分钟触发一次

    public void deleteBrowseData() {
        // 任务数据
        UserTaskDataDeleteThread linkDataThread = new UserTaskDataDeleteThread();
        linkDataThread.start();
    }

    /**
     * 查询当日审核支出 并插入表格
     */
    class UserTaskDataDeleteThread extends Thread {
        @Override
        public void run() {
            // 统计每日数据  插入每日数据表

            HfzPayDay pay=new HfzPayDay();
            pay.setAmount(hfzProfitPickRecordService.selectDayPay(new HfzProfitPickRecord()));
            SimpleDateFormat df = new SimpleDateFormat("dd");// 设置日期格式
            pay.setDayTime(df.format(new Date())+"日");
            hfzPayDayService.insert(pay);
            System.out.println("每日支出插入");
            super.run();
        }
    }

}
