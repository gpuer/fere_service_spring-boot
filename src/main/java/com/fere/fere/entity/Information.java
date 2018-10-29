package com.fere.fere.entity;


public class Information {

  private long informationId;
  private long uid;
  private String link_uid;
  private long link_status;
  private String emergencyContact;
  private String nickname;

  public long getInformationId() {
    return informationId;
  }

  public void setInformationId(long informationId) {
    this.informationId = informationId;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public String getLink_uid() {
    return link_uid;
  }

  public void setLink_uid(String link_uid) {
    this.link_uid = link_uid;
  }

  public long getLink_status() {
    return link_status;
  }

  public void setLink_status(long link_status) {
    this.link_status = link_status;
  }

  public String getEmergencyContact() {
    return emergencyContact;
  }

  public void setEmergencyContact(String emergencyContact) {
    this.emergencyContact = emergencyContact;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  @Override
  public String toString() {
    return "Information{" +
            "informationId=" + informationId +
            ", uid=" + uid +
            ", link_uid='" + link_uid + '\'' +
            ", link_status=" + link_status +
            ", emergencyContact='" + emergencyContact + '\'' +
            ", nickname='" + nickname + '\'' +
            '}';
  }
}
