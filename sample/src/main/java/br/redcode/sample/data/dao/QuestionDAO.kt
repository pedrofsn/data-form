package br.redcode.sample.data.dao

import androidx.room.Dao
import br.redcode.sample.data.entities.EntityQuestion

@Dao
interface QuestionDAO : BaseDAO<EntityQuestion> {

//    @Language("RoomSql")
//    @Query("SELECT question_id as idPlace, latitude, longitude, address FROM places where user_id like :idUser")
//    fun readAll(idUser: Long): LiveData<List<Place>>
//
//    @Language("RoomSql")
//    @Query("SELECT place_id as idPlace, latitude, longitude, address FROM places where place_id = :idPlace")
//    fun read(idPlace: Long): Place?

}