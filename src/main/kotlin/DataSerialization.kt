package pairExample


data class AgentInfo(
    val agentName: String ="",
    val address: String ="",
    val agentCeo: String ="",
    val agentTel: String ="",
    val agentArtist : MutableList<IdolInfo>
)

data class IdolInfo(
    val idolName : String = "",
    val idolDebut : String = "",
    val idolMember :List<String>
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