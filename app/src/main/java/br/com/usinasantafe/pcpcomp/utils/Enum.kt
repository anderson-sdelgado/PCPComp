package br.com.usinasantafe.pcpcomp.utils

enum class StatusData { OPEN, CLOSE  }
enum class StatusSend { STARTED, SEND, SENDING, SENT }
enum class StatusForeigner { INSIDE, OUTSIDE }
enum class TypeMov { INPUT, OUTPUT }
enum class StatusUpdate { UPDATED, FAILURE }
enum class StatusRecover { SUCCESS, EMPTY, FAILURE }
enum class PointerStart { MENUINICIAL, MENUAPONT }
enum class FlagUpdate { OUTDATED, UPDATED }
enum class TypeEquip { VEICULO, VEICULOSEG }
enum class TypeOcupante { MOTORISTA, PASSAGEIRO }
enum class TypeVisitTerc { VISITANTE, TERCEIRO }
enum class FlowApp { ADD, CHANGE }
enum class Errors { FIELDEMPTY, TOKEN, UPDATE, EXCEPTION, INVALID }
enum class TypeButton { NUMERIC, CLEAN, OK, UPDATE }
