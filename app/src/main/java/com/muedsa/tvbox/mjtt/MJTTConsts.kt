package com.muedsa.tvbox.mjtt

import com.muedsa.tvbox.api.data.MediaCatalogOption
import com.muedsa.tvbox.api.data.MediaCatalogOptionItem
import java.util.Calendar

val JumpUrlRegex = "window.location.href\\s*=\\s*\"(.*?)\"".toRegex()
const val CardWidth = 132
const val CardHeight = 160
const val ColorCardWidth = 240
const val ColorCardHeight = 85
val CardColors = listOf(
    0XFF_15_5A_32,
    0XFF_09_53_45,
    0XFF_15_43_61,
    0XFF_42_49_49,
    0XFF_78_42_13
)

val CatalogOptions: List<MediaCatalogOption> = listOf(
    MediaCatalogOption(
        name = "分类",
        value = "category",
        required = true,
        items = listOf(
            MediaCatalogOptionItem(
                name = "魔幻/科幻",
                value = "mhkh",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "灵异/惊悚",
                value = "lyjs",
            ),
            MediaCatalogOptionItem(
                name = "都市/情感",
                value = "dsqg",
            ),
            MediaCatalogOptionItem(
                name = "犯罪/历史",
                value = "fzls",
            ),
            MediaCatalogOptionItem(
                name = "选秀/综艺",
                value = "xxzy",
            ),
            MediaCatalogOptionItem(
                name = "动漫/卡通",
                value = "dmkt",
            ),
            MediaCatalogOptionItem(
                name = "电影",
                value = "dy",
            ),
        )
    ),
    MediaCatalogOption(
        name = "类型",
        value = "genre",
        required = true,
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "",
                defaultChecked = true,
            ),

            // 魔幻/科幻
            MediaCatalogOptionItem(
                name = "吸血鬼",
                value = "289",
            ),
            MediaCatalogOptionItem(
                name = "传记",
                value = "426",
            ),
            MediaCatalogOptionItem(
                name = "歌舞",
                value = "411",
            ),
            MediaCatalogOptionItem(
                name = "科幻",
                value = "238",
            ),
            MediaCatalogOptionItem(
                name = "动作",
                value = "239",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "240",
            ),
            MediaCatalogOptionItem(
                name = "魔幻",
                value = "253",
            ),
            MediaCatalogOptionItem(
                name = "青春",
                value = "254",
            ),
            MediaCatalogOptionItem(
                name = "冒险",
                value = "255",
            ),
            MediaCatalogOptionItem(
                name = "悬疑",
                value = "257",
            ),
            MediaCatalogOptionItem(
                name = "奇幻",
                value = "283",
            ),
            MediaCatalogOptionItem(
                name = "暴力",
                value = "286",
            ),
            MediaCatalogOptionItem(
                name = "血腥",
                value = "287",
            ),
            MediaCatalogOptionItem(
                name = "惊悚",
                value = "288",
            ),
            MediaCatalogOptionItem(
                name = "动画",
                value = "372",
            ),
            MediaCatalogOptionItem(
                name = "喜剧",
                value = "294",
            ),
            MediaCatalogOptionItem(
                name = "罪案",
                value = "299",
            ),
            MediaCatalogOptionItem(
                name = "历史",
                value = "300",
            ),
            MediaCatalogOptionItem(
                name = "迷你剧",
                value = "301",
            ),
            MediaCatalogOptionItem(
                name = "爱情",
                value = "306",
            ),
            MediaCatalogOptionItem(
                name = "战争",
                value = "316",
            ),
            MediaCatalogOptionItem(
                name = "古装",
                value = "317",
            ),
            MediaCatalogOptionItem(
                name = "医务",
                value = "340",
            ),
            MediaCatalogOptionItem(
                name = "西部",
                value = "343",
            ),
            MediaCatalogOptionItem(
                name = "史诗",
                value = "352",
            ),
            MediaCatalogOptionItem(
                name = "记录",
                value = "364",
            ),
            MediaCatalogOptionItem(
                name = "家庭",
                value = "366",
            ),

            // 灵异/惊悚
            MediaCatalogOptionItem(
                name = "罪案",
                value = "314",
            ),
            MediaCatalogOptionItem(
                name = "古装",
                value = "418",
            ),
            MediaCatalogOptionItem(
                name = "传记",
                value = "417",
            ),
            MediaCatalogOptionItem(
                name = "家庭",
                value = "408",
            ),
            MediaCatalogOptionItem(
                name = "惊悚",
                value = "249",
            ),
            MediaCatalogOptionItem(
                name = "奇幻",
                value = "251",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "252",
            ),
            MediaCatalogOptionItem(
                name = "悬疑",
                value = "263",
            ),
            MediaCatalogOptionItem(
                name = "魔幻",
                value = "307",
            ),
            MediaCatalogOptionItem(
                name = "喜剧",
                value = "308",
            ),
            MediaCatalogOptionItem(
                name = "吸血鬼",
                value = "309",
            ),
            MediaCatalogOptionItem(
                name = "血腥",
                value = "312",
            ),
            MediaCatalogOptionItem(
                name = "丧尸",
                value = "313",
            ),
            MediaCatalogOptionItem(
                name = "爱情",
                value = "376",
            ),
            MediaCatalogOptionItem(
                name = "科幻",
                value = "315",
            ),
            MediaCatalogOptionItem(
                name = "青春",
                value = "319",
            ),
            MediaCatalogOptionItem(
                name = "动作",
                value = "327",
            ),
            MediaCatalogOptionItem(
                name = "同性",
                value = "333",
            ),
            MediaCatalogOptionItem(
                name = "冒险",
                value = "344",
            ),
            MediaCatalogOptionItem(
                name = "暴力",
                value = "347",
            ),
            MediaCatalogOptionItem(
                name = "政治",
                value = "348",
            ),
            MediaCatalogOptionItem(
                name = "迷你剧",
                value = "353",
            ),
            MediaCatalogOptionItem(
                name = "历史",
                value = "360",
            ),
            MediaCatalogOptionItem(
                name = "记录",
                value = "365",
            ),
            MediaCatalogOptionItem(
                name = "医务",
                value = "369",
            ),

            // 都市/情感
            MediaCatalogOptionItem(
                name = "歌舞",
                value = "267",
            ),
            MediaCatalogOptionItem(
                name = "青春",
                value = "293",
            ),
            MediaCatalogOptionItem(
                name = "医务",
                value = "292",
            ),
            MediaCatalogOptionItem(
                name = "都市",
                value = "291",
            ),
            MediaCatalogOptionItem(
                name = "律政",
                value = "290",
            ),
            MediaCatalogOptionItem(
                name = "历史",
                value = "282",
            ),
            MediaCatalogOptionItem(
                name = "悬疑",
                value = "281",
            ),
            MediaCatalogOptionItem(
                name = "惊悚",
                value = "273",
            ),
            MediaCatalogOptionItem(
                name = "奇幻",
                value = "268",
            ),
            MediaCatalogOptionItem(
                name = "动作",
                value = "296",
            ),
            MediaCatalogOptionItem(
                name = "罪案",
                value = "266",
            ),
            MediaCatalogOptionItem(
                name = "同性",
                value = "265",
            ),
            MediaCatalogOptionItem(
                name = "科幻",
                value = "261",
            ),
            MediaCatalogOptionItem(
                name = "家庭",
                value = "260",
            ),
            MediaCatalogOptionItem(
                name = "爱情",
                value = "259",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "258",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "256",
            ),
            MediaCatalogOptionItem(
                name = "喜剧",
                value = "250",
            ),
            MediaCatalogOptionItem(
                name = "血腥",
                value = "334",
            ),
            MediaCatalogOptionItem(
                name = "丧尸",
                value = "368",
            ),
            MediaCatalogOptionItem(
                name = "谍战",
                value = "361",
            ),
            MediaCatalogOptionItem(
                name = "西部",
                value = "355",
            ),
            MediaCatalogOptionItem(
                name = "暴力",
                value = "354",
            ),
            MediaCatalogOptionItem(
                name = "传记",
                value = "350",
            ),
            MediaCatalogOptionItem(
                name = "政治",
                value = "342",
            ),
            MediaCatalogOptionItem(
                name = "动画",
                value = "341",
            ),
            MediaCatalogOptionItem(
                name = "记录",
                value = "339",
            ),
            MediaCatalogOptionItem(
                name = "吸血鬼",
                value = "371",
            ),
            MediaCatalogOptionItem(
                name = "迷你剧",
                value = "332",
            ),
            MediaCatalogOptionItem(
                name = "古装",
                value = "330",
            ),
            MediaCatalogOptionItem(
                name = "魔幻",
                value = "329",
            ),
            MediaCatalogOptionItem(
                name = "战争",
                value = "328",
            ),
            MediaCatalogOptionItem(
                name = "冒险",
                value = "326",
            ),
            MediaCatalogOptionItem(
                name = "真人秀",
                value = "320",
            ),
            MediaCatalogOptionItem(
                name = "情景喜剧",
                value = "318",
            ),

            // 犯罪/历史
            MediaCatalogOptionItem(
                name = "古装",
                value = "298",
            ),
            MediaCatalogOptionItem(
                name = "史诗",
                value = "409",
            ),
            MediaCatalogOptionItem(
                name = "罪案",
                value = "241",
            ),
            MediaCatalogOptionItem(
                name = "惊悚",
                value = "242",
            ),
            MediaCatalogOptionItem(
                name = "悬疑",
                value = "243",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "244",
            ),
            MediaCatalogOptionItem(
                name = "战争",
                value = "269",
            ),
            MediaCatalogOptionItem(
                name = "同性",
                value = "274",
            ),
            MediaCatalogOptionItem(
                name = "历史",
                value = "276",
            ),
            MediaCatalogOptionItem(
                name = "迷你剧",
                value = "278",
            ),
            MediaCatalogOptionItem(
                name = "动作",
                value = "279",
            ),
            MediaCatalogOptionItem(
                name = "冒险",
                value = "280",
            ),
            MediaCatalogOptionItem(
                name = "记录",
                value = "285",
            ),
            MediaCatalogOptionItem(
                name = "爱情",
                value = "295",
            ),
            MediaCatalogOptionItem(
                name = "古装",
                value = "297",
            ),
            MediaCatalogOptionItem(
                name = "歌舞",
                value = "362",
            ),
            MediaCatalogOptionItem(
                name = "家庭",
                value = "304",
            ),
            MediaCatalogOptionItem(
                name = "喜剧",
                value = "305",
            ),
            MediaCatalogOptionItem(
                name = "科幻",
                value = "310",
            ),
            MediaCatalogOptionItem(
                name = "谍战",
                value = "311",
            ),
            MediaCatalogOptionItem(
                name = "律政",
                value = "321",
            ),
            MediaCatalogOptionItem(
                name = "青春",
                value = "322",
            ),
            MediaCatalogOptionItem(
                name = "血腥",
                value = "323",
            ),
            MediaCatalogOptionItem(
                name = "暴力",
                value = "324",
            ),
            MediaCatalogOptionItem(
                name = "西部",
                value = "325",
            ),
            MediaCatalogOptionItem(
                name = "魔幻",
                value = "336",
            ),
            MediaCatalogOptionItem(
                name = "医务",
                value = "338",
            ),
            MediaCatalogOptionItem(
                name = "都市",
                value = "346",
            ),
            MediaCatalogOptionItem(
                name = "传记",
                value = "349",
            ),
            MediaCatalogOptionItem(
                name = "奇幻",
                value = "357",
            ),

            // 选秀/综艺
            MediaCatalogOptionItem(
                name = "综艺",
                value = "331",
            ),
            MediaCatalogOptionItem(
                name = "奇幻",
                value = "416",
            ),
            MediaCatalogOptionItem(
                name = "惊悚",
                value = "377",
            ),
            MediaCatalogOptionItem(
                name = "动画",
                value = "407",
            ),
            MediaCatalogOptionItem(
                name = "科幻",
                value = "410",
            ),
            MediaCatalogOptionItem(
                name = "记录",
                value = "245",
            ),
            MediaCatalogOptionItem(
                name = "传记",
                value = "275",
            ),
            MediaCatalogOptionItem(
                name = "真人秀",
                value = "284",
            ),
            MediaCatalogOptionItem(
                name = "悬疑",
                value = "302",
            ),
            MediaCatalogOptionItem(
                name = "喜剧",
                value = "303",
            ),
            MediaCatalogOptionItem(
                name = "动作",
                value = "373",
            ),
            MediaCatalogOptionItem(
                name = "歌舞",
                value = "335",
            ),
            MediaCatalogOptionItem(
                name = "青春",
                value = "337",
            ),
            MediaCatalogOptionItem(
                name = "罪案",
                value = "345",
            ),
            MediaCatalogOptionItem(
                name = "同性",
                value = "351",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "359",
            ),
            MediaCatalogOptionItem(
                name = "历史",
                value = "363",
            ),
            MediaCatalogOptionItem(
                name = "爱情",
                value = "367",
            ),
            MediaCatalogOptionItem(
                name = "冒险",
                value = "370",
            ),

            // 动漫/卡通
            MediaCatalogOptionItem(
                name = "冒险",
                value = "248",
            ),
            MediaCatalogOptionItem(
                name = "真人秀",
                value = "423",
            ),
            MediaCatalogOptionItem(
                name = "谍战",
                value = "419",
            ),
            MediaCatalogOptionItem(
                name = "同性",
                value = "415",
            ),
            MediaCatalogOptionItem(
                name = "魔幻",
                value = "414",
            ),
            MediaCatalogOptionItem(
                name = "歌舞",
                value = "412",
            ),
            MediaCatalogOptionItem(
                name = "战争",
                value = "405",
            ),
            MediaCatalogOptionItem(
                name = "历史",
                value = "406",
            ),
            MediaCatalogOptionItem(
                name = "家庭",
                value = "246",
            ),
            MediaCatalogOptionItem(
                name = "动画",
                value = "247",
            ),
            MediaCatalogOptionItem(
                name = "记录",
                value = "375",
            ),
            MediaCatalogOptionItem(
                name = "科幻",
                value = "262",
            ),
            MediaCatalogOptionItem(
                name = "奇幻",
                value = "264",
            ),
            MediaCatalogOptionItem(
                name = "罪案",
                value = "270",
            ),
            MediaCatalogOptionItem(
                name = "喜剧",
                value = "271",
            ),
            MediaCatalogOptionItem(
                name = "动作",
                value = "272",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "277",
            ),
            MediaCatalogOptionItem(
                name = "爱情",
                value = "356",
            ),
            MediaCatalogOptionItem(
                name = "惊悚",
                value = "358",
            ),
            MediaCatalogOptionItem(
                name = "悬疑",
                value = "374",
            ),

            // 电影
            MediaCatalogOptionItem(
                name = "冒险",
                value = "383",
            ),
            MediaCatalogOptionItem(
                name = "记录",
                value = "425",
            ),
            MediaCatalogOptionItem(
                name = "短片",
                value = "421",
            ),
            MediaCatalogOptionItem(
                name = "同性",
                value = "420",
            ),
            MediaCatalogOptionItem(
                name = "儿童",
                value = "399",
            ),
            MediaCatalogOptionItem(
                name = "剧情",
                value = "378",
            ),
            MediaCatalogOptionItem(
                name = "喜剧",
                value = "379",
            ),
            MediaCatalogOptionItem(
                name = "家庭",
                value = "380",
            ),
            MediaCatalogOptionItem(
                name = "历史",
                value = "400",
            ),
            MediaCatalogOptionItem(
                name = "武侠",
                value = "401",
            ),
            MediaCatalogOptionItem(
                name = "西部",
                value = "402",
            ),
            MediaCatalogOptionItem(
                name = "纪录",
                value = "403",
            ),
            MediaCatalogOptionItem(
                name = "预告",
                value = "404",
            ),
            MediaCatalogOptionItem(
                name = "爱情",
                value = "381",
            ),
            MediaCatalogOptionItem(
                name = "动作",
                value = "382",
            ),
            MediaCatalogOptionItem(
                name = "传记",
                value = "398",
            ),
            MediaCatalogOptionItem(
                name = "动画",
                value = "384",
            ),
            MediaCatalogOptionItem(
                name = "犯罪",
                value = "385",
            ),
            MediaCatalogOptionItem(
                name = "恐怖",
                value = "386",
            ),
            MediaCatalogOptionItem(
                name = "悬疑",
                value = "387",
            ),
            MediaCatalogOptionItem(
                name = "惊悚",
                value = "388",
            ),
            MediaCatalogOptionItem(
                name = "奇幻",
                value = "389",
            ),
            MediaCatalogOptionItem(
                name = "歌舞",
                value = "390",
            ),
            MediaCatalogOptionItem(
                name = "战争",
                value = "391",
            ),
            MediaCatalogOptionItem(
                name = "运动",
                value = "392",
            ),
            MediaCatalogOptionItem(
                name = "灾难",
                value = "393",
            ),
            MediaCatalogOptionItem(
                name = "古装",
                value = "394",
            ),
            MediaCatalogOptionItem(
                name = "音乐",
                value = "395",
            ),
            MediaCatalogOptionItem(
                name = "科幻",
                value = "396",
            ),
            MediaCatalogOptionItem(
                name = "未知",
                value = "397",
            ),
        ),
    ),
    MediaCatalogOption(
        name = "地区",
        value = "region",
        required = true,
        items = listOf(
            MediaCatalogOptionItem(
                name = "全部",
                value = "",
                defaultChecked = true,
            ),
            MediaCatalogOptionItem(
                name = "美国",
                value = "美国",
            ),
            MediaCatalogOptionItem(
                name = "英国",
                value = "英国",
            ),
            MediaCatalogOptionItem(
                name = "加拿大",
                value = "加拿大",
            ),
            MediaCatalogOptionItem(
                name = "澳大利亚",
                value = "澳大利亚",
            ),
            MediaCatalogOptionItem(
                name = "西班牙",
                value = "西班牙",
            ),
            MediaCatalogOptionItem(
                name = "韩国",
                value = "韩国",
            ),
            MediaCatalogOptionItem(
                name = "法国",
                value = "法国",
            ),
            MediaCatalogOptionItem(
                name = "德国",
                value = "德国",
            ),
            MediaCatalogOptionItem(
                name = "巴西",
                value = "巴西",
            ),
            MediaCatalogOptionItem(
                name = "挪威",
                value = "挪威",
            ),
            MediaCatalogOptionItem(
                name = "意大利",
                value = "意大利",
            ),
            MediaCatalogOptionItem(
                name = "墨西哥",
                value = "墨西哥",
            ),
            MediaCatalogOptionItem(
                name = "俄罗斯",
                value = "俄罗斯",
            ),
            MediaCatalogOptionItem(
                name = "哥伦比亚",
                value = "哥伦比亚",
            ),
            MediaCatalogOptionItem(
                name = "土耳其",
                value = "土耳其",
            ),
            MediaCatalogOptionItem(
                name = "以色列",
                value = "以色列",
            ),
            MediaCatalogOptionItem(
                name = "台湾",
                value = "台湾",
            ),
            MediaCatalogOptionItem(
                name = "其他",
                value = "其他",
            ),
        )
    ),
    MediaCatalogOption(
        name = "年代",
        value = "year",
        required = true,
        items = buildList {
            add(
                MediaCatalogOptionItem(
                    name = "全部",
                    value = "",
                    defaultChecked = true
                )
            )
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            for (year in currentYear downTo 2000) {
                add(
                    MediaCatalogOptionItem(
                        name = "$year",
                        value = "$year",
                    )
                )
            }
            add(
                MediaCatalogOptionItem(
                    name = "90年代",
                    value = "1990,1999",
                )
            )
            add(
                MediaCatalogOptionItem(
                    name = "80年代",
                    value = "1980,1989",
                )
            )
            add(
                MediaCatalogOptionItem(
                    name = "更早",
                    value = "1900,1980",
                )
            )
        },
    ),
    MediaCatalogOption(
        name = "排序",
        value = "order",
        required = true,
        items = listOf(
            MediaCatalogOptionItem(
                name = "最新",
                value = "addtime",
                defaultChecked = true
            ),
            MediaCatalogOptionItem(
                name = "热门",
                value = "hits",
            ),
        ),
    )
)