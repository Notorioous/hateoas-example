package com.example.springrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column
  private String title;
  @Column(name = "short_text")
  private String shortText;
  @Column
  private String text;
  @Column(name = "created_date")
  private String createdDate;
  @ManyToOne
  private Category category;
  @Column(name = "pic_url")
  private String picUrl;
  @ManyToOne
  @JsonIgnore
  private User user;

}
