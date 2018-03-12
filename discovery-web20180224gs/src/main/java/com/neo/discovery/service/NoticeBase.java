package com.neo.discovery.service;

import com.neo.discovery.util.mail.MailUtil;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 3/10/2018.
 */
@Service("noticeBase")
public class NoticeBase {

    public static MailUtil mailUtil = new MailUtil();
}
