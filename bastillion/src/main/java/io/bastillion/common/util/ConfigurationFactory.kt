package io.bastillion.common.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.InputStream


class ConfigurationFactory() {

    fun config(): Config {
        val inputStream: InputStream = this.javaClass
                .getClassLoader().getResourceAsStream("application.yml")
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerKotlinModule()
        return mapper.readValue<Config>(inputStream, Config::class.java)
    }

    companion object fileFormats {
        fun forFormat(): ObjectMapper {
            return ObjectMapper(YAMLFactory())
        }
    }
}