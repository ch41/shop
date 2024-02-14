package com.feature.registration.data.mapper

import com.core.local.database.CredentialsEntity
import com.feature.registration.domain.models.Credentials


fun CredentialsEntity.toCredentials() = Credentials(username, surname, phone)

fun Credentials.toCredentialsEntity() = CredentialsEntity(username, surname, phoneNumber)

fun List<CredentialsEntity>.toCredentials() =
    map { Credentials(username = it.username, surname = it.surname, phoneNumber = it.phone) }
