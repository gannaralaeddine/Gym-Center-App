package com.example.gymcenterapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;
import java.io.Serializable;
import java.util.Set;

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
    @Column(length = 510)
    private String catDescription;

    private String catImage;


    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    //@JoinColumn(name = "categoryId")
    private List<Activity> categoryActivities;




    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "category_images",
        joinColumns = { @JoinColumn (name = "category_id") },
        inverseJoinColumns = { @JoinColumn(name = "image_id") })
    private Set<ImageModel> images;
}
