package br.com.usinasantafe.pcpcomp.domain.usecases.initial

interface CheckAccessMain {
    suspend operator fun invoke(): Result<Boolean>
}