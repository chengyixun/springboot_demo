package com.example.jpamultitenancy.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.notification.EventType;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.IAsyncResult;
import microsoft.exchange.webservices.data.notification.FolderEvent;
import microsoft.exchange.webservices.data.notification.GetEventsResults;
import microsoft.exchange.webservices.data.notification.ItemEvent;
import microsoft.exchange.webservices.data.notification.PullSubscription;
import microsoft.exchange.webservices.data.notification.StreamingSubscription;
import microsoft.exchange.webservices.data.notification.StreamingSubscriptionConnection;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.property.complex.recurrence.pattern.Recurrence;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ExchangeUtil @Author: amy @Description: ExchangeUtil @Date: 2021/6/24 @Version: 1.0
 */
@Slf4j
public class ExchangeMailUtil {

  // 邮件服务器地址
  private String mailServer;
  // 用户名
  private String user;
  // 密码
  private String password;
  // 使用的方式，默认可用不填
  private String domain;

  /** 构造方法 */
  public ExchangeMailUtil(String mailServer, String user, String password) {
    this.mailServer = mailServer;
    this.user = user;
    this.password = password;
  }

  public ExchangeMailUtil(String user, String password) {
    this.user = user;
    this.password = password;
  }

  /** 构造方法 */
  public ExchangeMailUtil(String mailServer, String user, String password, String domain) {
    this.mailServer = mailServer;
    this.user = user;
    this.password = password;
    this.domain = domain;
  }

  /**
   * 创建邮件服务
   *
   * @return 邮件服务
   */
  private ExchangeService getExchangeService() {
    ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
    // 用户认证信息
    ExchangeCredentials credentials;
    if (StringUtils.isEmpty(domain)) {
      credentials = new WebCredentials(user, password);
    } else {
      credentials = new WebCredentials(user, password, domain);
    }
    service.setCredentials(credentials);
    try {
      // service.setUrl(new URI(mailServer));
      // service.autodiscoverUrl("<your_email_address>");
      service.autodiscoverUrl("Amy.Wang@gaiaworks.cn", new RedirectionUrlCallback());
      // service.setTraceEnabled(true);
    } catch (Exception e) {
      log.error("getExchangeService error:{}", e);
    }

    return service;
  }

  /**
   * 收取邮件
   *
   * @param max 最大收取邮件数
   * @throws Exception
   */
  public ArrayList<EmailMessage> receive(int max) throws Exception {
    ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
    ExchangeCredentials credentials = new WebCredentials(user, password);
    service.setCredentials(credentials);
    service.setUrl(new URI(mailServer));
    // 绑定收件箱,同样可以绑定发件箱
    Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
    // 获取文件总数量
    int count = inbox.getTotalCount();
    if (max > 0) {
      count = count > max ? max : count;
    }
    // 循环获取邮箱邮件
    ItemView view = new ItemView(count);
    FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);
    ArrayList<EmailMessage> result = new ArrayList<>();
    for (Item item : findResults.getItems()) {
      EmailMessage message = EmailMessage.bind(service, item.getId());
      result.add(message);
    }
    return result;
  }

  /**
   * 收取所有邮件
   *
   * @throws Exception
   */
  public ArrayList<EmailMessage> receive() throws Exception {
    return receive(0);
  }

  /**
   * 发送带附件的mail
   *
   * @param subject 邮件标题
   * @param to 收件人列表
   * @param cc 抄送人列表
   * @param bodyText 邮件内容
   * @param attachmentPaths 附件地址列表
   * @throws Exception
   */
  public void send(
      String subject, String[] to, String[] cc, String bodyText, String[] attachmentPaths) {
    ExchangeService service = getExchangeService();
    EmailMessage msg = null;
    try {
      msg = new EmailMessage(service);
      msg.setSubject(subject);
      MessageBody body = MessageBody.getMessageBodyFromText(bodyText);
      body.setBodyType(BodyType.HTML);
      msg.setBody(body);
      for (String toPerson : to) {
        msg.getToRecipients().add(toPerson);
      }
      if (cc != null) {
        for (String ccPerson : cc) {
          msg.getCcRecipients().add(ccPerson);
        }
      }
      if (attachmentPaths != null) {
        for (String attachmentPath : attachmentPaths) {
          msg.getAttachments().addFileAttachment(attachmentPath);
        }
      }
      msg.send();
    } catch (Exception e) {
      log.error("send fail:{}", e);
    }
  }

  /**
   * 发送不带抄送人的邮件
   *
   * @param subject 标题
   * @param to 收件人列表
   * @param bodyText 邮件内容
   * @throws Exception
   */
  public void send(String subject, String[] to, String bodyText) {
    send(subject, to, null, bodyText);
  }

  /**
   * 发送不带附件的mail
   *
   * @param subject 邮件标题
   * @param to 收件人列表
   * @param cc 抄送人列表
   * @param bodyText 邮件内容
   * @throws Exception
   */
  public void send(String subject, String[] to, String[] cc, String bodyText) {
    send(subject, to, cc, bodyText, null);
  }

  /** create calendar */
  public void createCalendar() {
    ExchangeService service = getExchangeService();
    CalendarFolder folder = null;
    try {
      folder = new CalendarFolder(service);
      folder.setDisplayName("Test");
      folder.save(WellKnownFolderName.PublicFoldersRoot);

      CalendarFolder calendar = CalendarFolder.bind(service, folder.getId());
      System.out.println("calendar：" + calendar);

    } catch (Exception e) {
      log.error("getCalendar error:{}", e);
    }
  }

  public void testStream() {
    ExchangeService service = getExchangeService();

    List folder = Lists.newArrayList();
    folder.add(new FolderId(WellKnownFolderName.Inbox));
    try {
      StreamingSubscription subscription =
          service.subscribeToStreamingNotifications(
              folder,
              EventType.NewMail,
              EventType.Created,
              EventType.Deleted,
              EventType.Modified,
              EventType.Moved,
              EventType.Copied,
              EventType.FreeBusyChanged);

      StreamingSubscriptionConnection connection = new StreamingSubscriptionConnection(service, 30);

      log.info("isOpen:{}", connection.getIsOpen());

      connection.addSubscription(subscription);
      // connection.addOnNotificationEvent();
      // connection.OnDisconnect += OnDisconnect;
      connection.open();

      EmailMessage msg = new EmailMessage(service);
      msg.setSubject("Testing Streaming Notification on 16 Aug 2010");
      msg.setBody(MessageBody.getMessageBodyFromText("Streaming Notification -------"));
      msg.getToRecipients().add("Amy.Wang@gaiaworks.cn");
      msg.send();
      Thread.sleep(3 * 1000);
      // connection.close();
      System.out.println("end........");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void testCreateStreamingSubscription() {
    ExchangeService service = getExchangeService();

    List folder = Lists.newArrayList();
    folder.add(new FolderId(WellKnownFolderName.Calendar));

    try {
      StreamingSubscription subscription =
          service.subscribeToStreamingNotifications(
              folder,
              EventType.NewMail,
              EventType.Created,
              EventType.Deleted,
              EventType.Modified,
              EventType.Moved,
              EventType.Copied,
              EventType.FreeBusyChanged);

      createStreamingSubscription(service, subscription);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void createStreamingSubscription(
      ExchangeService service, StreamingSubscription subscription) {
    StreamingSubscriptionConnection connection = null;
    try {
      connection = new StreamingSubscriptionConnection(service, 30);
      connection.addSubscription(subscription);
      // connection.onNotificationEvent
      // connection.onSubscriptionError += OnSubscriptionError;
      // connection.OnDisconnect += OnDisconnect;
      // connection.addOnDisconnect(subscription);
      connection.open();
      log.info("isOpen:{}", connection.getIsOpen());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ExchangeMailUtil mailUtil = new ExchangeMailUtil("Amy.Wang@gaiaworks.cn", "Gaia@2021");

    // send simple email
    mailUtil.send("TEST", new String[] {"Amy.Wang@gaiaworks.cn"}, "测试!!!");
    // create appointment
    // mailUtil.testAppointment();
    // mailUtil.testFolder();

    // mailUtil.createFolder();
    // mailUtil.pageThroughEntireInbox();
    // mailUtil.findChildFolders();
    // mailUtil.findAppointments();

    // mailUtil.testPull();
    // mailUtil.testStream();
    mailUtil.testPull();
    // mailUtil.testCreateStreamingSubscription();

  }

  /** */
  public void listFirstTenItems() {
    ExchangeService service = getExchangeService();
    try {
      // WellKnownFolderName.inbox 收件箱文件夹
      Folder folder = Folder.bind(service, WellKnownFolderName.Calendar);

      ItemView view = new ItemView(10);
      FindItemsResults<Item> findResults = service.findItems(folder.getId(), view);

      // MOOOOOOST IMPORTANT: load messages' properties before
      service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);

      for (Item item : findResults.getItems()) {
        // Do something with the item as shown
        System.out.println("id==========" + item.getId());
        System.out.println("sub==========" + item.getSubject());
      }
    } catch (Exception e) {
      log.error("create folder:{}", e);
    }
  }

  public void pageThroughEntireInbox() {
    ExchangeService service = getExchangeService();
    ItemView view = new ItemView(4);
    FindItemsResults<Item> findItemsResults;
    try {
      do {

        findItemsResults = service.findItems(WellKnownFolderName.Inbox, view);
        for (Item item : findItemsResults.getItems()) {
          // Do something with the item.
          System.out.println("id==========" + item.getId());
          System.out.println("sub==========" + item.getSubject());
        }
        view.setOffset(view.getOffset() + 4);
        log.info(
            ">>>total:{},offset:{},pageSize:{}",
            findItemsResults.getTotalCount(),
            view.getOffset(),
            view.getPageSize());
      } while (findItemsResults.isMoreAvailable());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** 创建folder */
  public void createFolder() {
    ExchangeService service = getExchangeService();
    Folder folder = null;
    try {
      folder = new Folder(service);
      folder.setDisplayName("EWS-JAVA-Folder");
      // creates the folder as a child of the Inbox folder.
      folder.save(WellKnownFolderName.Inbox);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void findChildFolders() {
    ExchangeService service = getExchangeService();
    try {
      FindFoldersResults folders =
          service.findFolders(WellKnownFolderName.Inbox, new FolderView(Integer.MAX_VALUE));

      for (Folder folder : folders.getFolders()) {
        System.out.println("Count======" + folder.getChildFolderCount());
        System.out.println("Name=======" + folder.getDisplayName());
      }
    } catch (Exception e) {

      e.printStackTrace();
    }
  }

  public void findAppointments() {
    ExchangeService service = getExchangeService();
    CalendarFolder cf = null;
    try {
      cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
      FindItemsResults<Appointment> findResults =
          cf.findAppointments(
              new CalendarView(
                  DateUtils.parse("2021-06-01 00:00:00"), DateUtils.parse("2021-08-01 00:00:00")));
      for (Appointment appt : findResults.getItems()) {
        appt.load(PropertySet.FirstClassProperties);
        System.out.println("SUBJECT=====" + appt.getSubject());
        System.out.println("BODY========" + appt.getBody());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void testPull() {
    ExchangeService service = getExchangeService();
    // send("Test-Event", new String[] { "Amy.Wang@gaiaworks.cn" }, "测试!!!");

    List folder = Lists.newArrayList();
    folder.add(new FolderId(WellKnownFolderName.Inbox));

    try {
      IAsyncResult iAsyncResult =
          service.beginSubscribeToPullNotificationsOnAllFolders(
              null, null, 5, null, EventType.NewMail, EventType.Created, EventType.Deleted);

      PullSubscription subscription = service.endSubscribeToPullNotifications(iAsyncResult);
      /*
       * PullSubscription subscription = service.subscribeToPullNotifications(folder,
       * 1440, null, EventType.NewMail, EventType.Created, EventType.Deleted);
       */

      GetEventsResults events = subscription.getEvents();

      System.out.println("events=====" + events.getItemEvents());

      for (ItemEvent itemEvent : events.getItemEvents()) {
        if (itemEvent.getEventType() == EventType.NewMail) {
          EmailMessage message = EmailMessage.bind(service, itemEvent.getItemId());
          System.out.println("new Mail:" + message.getSubject());
        } else if (itemEvent.getEventType() == EventType.Created) {
          Item item = Item.bind(service, itemEvent.getItemId());
          System.out.println("create:" + item.getSubject());
        } else if (itemEvent.getEventType() == EventType.Deleted) {
          System.out.println("create:" + itemEvent.getEventType().name());
          break;
        }
      }
      // Loop through all folder-related events.
      for (FolderEvent folderEvent : events.getFolderEvents()) {
        if (folderEvent.getEventType() == EventType.Created) {
          Folder folder1 = Folder.bind(service, folderEvent.getFolderId());
        } else if (folderEvent.getEventType() == EventType.Deleted) {
          System.out.println("folder  deleted" + folderEvent.getFolderId().getUniqueId());
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void testPush() {
    ExchangeService service = getExchangeService();
    // service.subscribeToPushNotifications()

  }

  public void testStreamingNotification() {}

  static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
    @Override
    public boolean autodiscoverRedirectionUrlValidationCallback(String redirectionUrl) {
      return redirectionUrl.toLowerCase().startsWith("https://");
    }
  }

  /** 创建会议 并加入日历 */
  public void testAppointment() {
    ExchangeService service = getExchangeService();
    Appointment appointment = null;
    try {
      appointment = new Appointment(service);
      appointment.setSubject("Recurrence Appointment for JAVA XML TEST");
      appointment.setBody(MessageBody.getMessageBodyFromText("Recurrence Test Body Msg"));

      appointment.setStart(DateUtils.parse("2021-07-01 12:00:00"));
      appointment.setEnd(DateUtils.parse("2021-07-01 13:00:00"));
      // 重复
      appointment.setRecurrence(new Recurrence.DailyPattern(appointment.getStart(), 3));

      appointment.getRecurrence().setStartDate(appointment.getStart());
      appointment.getRecurrence().setEndDate(DateUtils.toDate(LocalDate.of(2021, 07, 01)));
      appointment.save();
      // 邀请人参加这个会议
      appointment.getRequiredAttendees().add("Ackerman.Liu@gaiaworks.cn");
      appointment.update(ConflictResolutionMode.AutoResolve);
    } catch (Exception e) {
      log.error("testAppointment error:{}", e);
    }
  }
}
