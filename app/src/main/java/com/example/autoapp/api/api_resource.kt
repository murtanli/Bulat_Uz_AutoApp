package com.example.autoapp.api

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class api_resource {

    suspend fun logIn(login: String, password: String): LoginResponse {
        val apiUrl = "http://194.67.78.48:8100/login/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"login\":\"$login\",\"password\":\"$password\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                gson.fromJson(response.toString(), LoginResponse::class.java)
            } catch (e: Exception) {
                Log.e("LoginError", "Error fetching or parsing login data ", e)
                throw e
            }
        }
    }

    suspend fun Sign_in(login: String, password: String): LoginResponse {
        val apiUrl = "http://194.67.78.48:8100/sign_in/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"login\":\"$login\",\"password\":\"$password\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                gson.fromJson(response.toString(), LoginResponse::class.java)
            } catch (e: Exception) {
                Log.e("LoginError", "Error fetching or parsing login data ", e)
                throw e
            }
        }
    }

    suspend fun get_all_cars(): List<All_cars> {
        val apiUrl = "http://194.67.78.48:8100/get_all_cars/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                val newsResponse = gson.fromJson(response.toString(), Array<All_cars>::class.java)
                newsResponse.toList()
            } catch (e: Exception) {
                Log.e("NewsError", "Error fetching or parsing news data", e)
                throw e
            }
        }
    }


    suspend fun get_all_cars_filters(owners_count: Int, brand: String, car_type: String): List<All_cars> {
        val apiUrl = "http://194.67.78.48:8100/get_all_cars_filters/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"owners_count\":\"$owners_count\",\"brand\":\"$brand\",\"car_type\":\"$car_type\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                val newsResponse = gson.fromJson(response.toString(), Array<All_cars>::class.java)
                newsResponse.toList()
            } catch (e: Exception) {
                Log.e("NewsError", "Error fetching or parsing news data", e)
                throw e
            }
        }
    }

    suspend fun get_all_saved_cars(user_id: Int?): List<All_cars> {
        val apiUrl = "http://194.67.78.48:8100/get_saved_car/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"user_id\":\"$user_id\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                val newsResponse = gson.fromJson(response.toString(), Array<All_cars>::class.java)
                newsResponse.toList()
            } catch (e: Exception) {
                Log.e("NewsError", "Error fetching or parsing news data", e)
                throw e
            }
        }
    }

    suspend fun get_car_id(car_id: Int?): All_cars {
        val apiUrl = "http://194.67.78.48:8100/get_car_id/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"car_id\":\"$car_id\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                val newsResponse = gson.fromJson(response.toString(), All_cars::class.java)
                newsResponse
            } catch (e: Exception) {
                Log.e("NewsError", "Error fetching or parsing news data", e)
                throw e
            }
        }
    }

    suspend fun save_car_id(user_id: Int?, car_id: Int): SaveResponse {
        val apiUrl = "http://194.67.78.48:8100/save_car/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"user_id\":\"$user_id\", \"car_id\":\"$car_id\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                val newsResponse = gson.fromJson(response.toString(), SaveResponse::class.java)
                newsResponse
            } catch (e: Exception) {
                Log.e("NewsError", "Error fetching or parsing news data", e)
                throw e
            }
        }
    }
}