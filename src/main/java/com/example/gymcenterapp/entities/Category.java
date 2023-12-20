package com.example.gymcenterapp.entities;

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
@Table(name = "Catégorie")
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

    @ElementCollection
    @OneToMany(mappedBy = "category")
    private List<Activity> catActivities;
}
