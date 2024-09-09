package com.example.gymcenterapp.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String imageName;

    private String imageType;

    private Long imageSize;

    private String imageUrl;

}
