package cn.zjujri.workday.module;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CurrentWeather", title = "当前天气")
public record CurrentWeather(
    @Schema(title = "气温")
    Double temperature,
    @Schema(title = "风速")
    Double windspeed,
    @Schema(title = "风向")
    Double winddirection,
    @Schema(title = "天气码",description = "wmo code 标准码 https://open-meteo.com/en/doc 底部")
    Integer weathercode,
    @Schema(title = "天气",description = "根据code转换后")
    String weather,
    @Schema(title = "时间")
    String time
) {
    
}
