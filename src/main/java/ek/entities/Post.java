package ek.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
@Data
@AllArgsConstructor

public class Post {
    private int id;
    private String user_id;
    private String title;
    private String content;
    private int upvotes;
  }
