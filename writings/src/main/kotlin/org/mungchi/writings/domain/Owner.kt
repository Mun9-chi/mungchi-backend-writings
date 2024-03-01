package org.mungchi.writings.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Owner(

    @Column
    val nickName : String,

    @Column
    val imageUrl : String
) {
}
