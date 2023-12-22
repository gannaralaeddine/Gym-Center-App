package com.example.gymcenterapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Embeddable
public class Category implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "categoryId")
    private Long catId;

    @JoinColumn(name = "categoryName")
    private String catName;

    @JoinColumn(name = "categoryDescription")
    private String catDescription;

    @JoinColumn(name = "categoryImage")
    private String catImage;


    @JsonIgnore
    @OneToMany(mappedBy = "category")
    //@JoinColumn(name = "categoryId")
    private List<Activity> categoryActivities;
}
