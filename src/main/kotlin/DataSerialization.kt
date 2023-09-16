package pairExample


data class AgentInfo(
    val agentName: String ="",
    val address: String ="",
    val agentCeo: String ="",
    val agentTel: String ="",
//    val agentArtist: MutableList<IdolInfo>? // 일대다 관계
)

data class IdolInfo(
    val idolName: String = "",
    val idolDebut: String = "",
    val idolMember:List<String>,
    val agent: String = "" // 소속사와 연관 관계 (relation)
)

data class EventInfo(
    val eventName : String = "",
    val eventDate : String = "",
    val eventGuest : String = ""
)

//data class MemberInfo(
//    val memberName : String = "",
//    val memberBirth : String = ""
//):Serializable