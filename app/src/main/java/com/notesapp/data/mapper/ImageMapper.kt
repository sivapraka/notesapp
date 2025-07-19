package com.notesapp.data.mapper

import com.notesapp.data.local.entity.ImageConfigEntity
import com.notesapp.domain.model.ImageConfig

// Entity → Domain
fun ImageConfigEntity.toDomain(): ImageConfig {
    return ImageConfig(
        baseUrl = baseUrl,
        secureBaseUrl = secureBaseUrl,
        backdropSizes = backdropSizes,
        logoSizes = logoSizes,
        posterSizes = posterSizes,
        profileSizes = profileSizes,
        stillSizes = stillSizes
    )
}

// Domain → Entity (optional, for testing or updates)
fun ImageConfig.toEntity(): ImageConfigEntity {
    return ImageConfigEntity(
        baseUrl = baseUrl,
        secureBaseUrl = secureBaseUrl,
        backdropSizes = backdropSizes,
        logoSizes = logoSizes,
        posterSizes = posterSizes,
        profileSizes = profileSizes,
        stillSizes = stillSizes
    )
}
