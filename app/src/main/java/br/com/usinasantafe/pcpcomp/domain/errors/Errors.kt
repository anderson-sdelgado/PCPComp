package br.com.usinasantafe.pcpcomp.domain.errors

class UsecaseException(message: String = "Failure Usecase", cause: Throwable? = null) : Exception(message, cause)

class RepositoryException(message: String = "Failure Repository", cause: Throwable? = null) : Exception(message, cause)

class DatasourceException(message: String = "Failure Datasource", cause: Throwable? = null) : Exception(message, cause)