package com.hnam.recyclerview

/**
 * Created by nampham on 4/20/19.
 */
class FakeService {

    companion object{
        fun getMovieRaw() : String = "{\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"vote_count\": 164,\n" +
                "            \"id\": 456740,\n" +
                "            \"video\": true,\n" +
                "            \"vote_average\": 5.2,\n" +
                "            \"title\": \"Hellboy\",\n" +
                "            \"popularity\": 373.147,\n" +
                "            \"poster_path\": \"/nUXCJMnAiwCpNPZuJH2n6h5hGtF.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Hellboy\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                14\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/5BkSkNtfrnTuKOtTaZhl8avn4wU.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Hellboy comes to England, where he must defeat Nimue, Merlin's consort and the Blood Queen. But their battle will bring about the end of the world, a fate he desperately tries to turn away.\",\n" +
                "            \"release_date\": \"2019-04-10\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 1041,\n" +
                "            \"id\": 287947,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.3,\n" +
                "            \"title\": \"Shazam!\",\n" +
                "            \"popularity\": 325.111,\n" +
                "            \"poster_path\": \"/xnopI5Xtky18MPhK40cZAGAOVeV.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Shazam!\",\n" +
                "            \"genre_ids\": [\n" +
                "                35,\n" +
                "                12,\n" +
                "                14\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"A boy is given the ability to become an adult superhero in times of need with a single magic word.\",\n" +
                "            \"release_date\": \"2019-03-23\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 348,\n" +
                "            \"id\": 537915,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.5,\n" +
                "            \"title\": \"After\",\n" +
                "            \"popularity\": 299.895,\n" +
                "            \"poster_path\": \"/u3B2YKUjWABcxXZ6Nm9h10hLUbh.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"After\",\n" +
                "            \"genre_ids\": [\n" +
                "                18,\n" +
                "                10749\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/8lESY7UGpOsbL2caib9Qe4bOebF.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"A young woman falls for a guy with a dark secret and the two embark on a rocky relationship.\",\n" +
                "            \"release_date\": \"2019-04-11\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 3931,\n" +
                "            \"id\": 299537,\n" +
                "            \"video\": true,\n" +
                "            \"vote_average\": 7.2,\n" +
                "            \"title\": \"Captain Marvel\",\n" +
                "            \"popularity\": 243.105,\n" +
                "            \"poster_path\": \"/AtsgWhDnHTq68L0lLsUrCnM7TjG.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Captain Marvel\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                878\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/w2PMyoyLU22YvrGK3smVM9fW1jj.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"The story follows Carol Danvers as she becomes one of the universe’s most powerful heroes when Earth is caught in the middle of a galactic war between two alien races. Set in the 1990s, Captain Marvel is an all-new adventure from a previously unseen period in the history of the Marvel Cinematic Universe.\",\n" +
                "            \"release_date\": \"2019-03-06\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 1510,\n" +
                "            \"id\": 166428,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.6,\n" +
                "            \"title\": \"How to Train Your Dragon: The Hidden World\",\n" +
                "            \"popularity\": 204.182,\n" +
                "            \"poster_path\": \"/xvx4Yhf0DVH8G4LzNISpMfFBDy2.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"How to Train Your Dragon: The Hidden World\",\n" +
                "            \"genre_ids\": [\n" +
                "                16,\n" +
                "                10751,\n" +
                "                12\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/h3KN24PrOheHVYs9ypuOIdFBEpX.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.\",\n" +
                "            \"release_date\": \"2019-01-03\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 0,\n" +
                "            \"id\": 299534,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 0,\n" +
                "            \"title\": \"Avengers: Endgame\",\n" +
                "            \"popularity\": 168.315,\n" +
                "            \"poster_path\": \"/or06FN3Dka5tukK1e9sl16pB3iy.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Avengers: Endgame\",\n" +
                "            \"genre_ids\": [\n" +
                "                12,\n" +
                "                878,\n" +
                "                28\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.\",\n" +
                "            \"release_date\": \"2019-04-24\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 841,\n" +
                "            \"id\": 522681,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.3,\n" +
                "            \"title\": \"Escape Room\",\n" +
                "            \"popularity\": 125.242,\n" +
                "            \"poster_path\": \"/8yZAx7tlKRZIg7pJfaPhl00yHIQ.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Escape Room\",\n" +
                "            \"genre_ids\": [\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/mWLljCmpqlcYQh7uh51BBMwCZwN.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Six strangers find themselves in circumstances beyond their control, and must use their wits to survive.\",\n" +
                "            \"release_date\": \"2019-01-03\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 885,\n" +
                "            \"id\": 329996,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.7,\n" +
                "            \"title\": \"Dumbo\",\n" +
                "            \"popularity\": 118.361,\n" +
                "            \"poster_path\": \"/279PwJAcelI4VuBtdzrZASqDPQr.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Dumbo\",\n" +
                "            \"genre_ids\": [\n" +
                "                12,\n" +
                "                10751,\n" +
                "                14\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/5tFt6iuGnKapHl5tw0X0cKcnuVo.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"A young elephant, whose oversized ears enable him to fly, helps save a struggling circus, but when the circus plans a new venture, Dumbo and his friends discover dark secrets beneath its shiny veneer.\",\n" +
                "            \"release_date\": \"2019-03-27\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 1083,\n" +
                "            \"id\": 458723,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.2,\n" +
                "            \"title\": \"Us\",\n" +
                "            \"popularity\": 114.238,\n" +
                "            \"poster_path\": \"/ux2dU1jQ2ACIMShzB3yP93Udpzc.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Us\",\n" +
                "            \"genre_ids\": [\n" +
                "                53,\n" +
                "                27\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/jNUCddkM1fjYcFIcEwFjc7s2H4V.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Husband and wife Gabe and Adelaide Wilson take their kids to their beach house expecting to unplug and unwind with friends. But as night descends, their serenity turns to tension and chaos when some shocking visitors arrive uninvited.\",\n" +
                "            \"release_date\": \"2019-03-14\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 53,\n" +
                "            \"id\": 411728,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.4,\n" +
                "            \"title\": \"The Professor and the Madman\",\n" +
                "            \"popularity\": 103.657,\n" +
                "            \"poster_path\": \"/yBaf5WjUqTQbc6mzPQBUcBiauDI.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"The Professor and the Madman\",\n" +
                "            \"genre_ids\": [\n" +
                "                18,\n" +
                "                36,\n" +
                "                9648\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/5Zjh2jPZyN0tuXmKXKuLEWy0k2S.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Professor James Murray begins work compiling words for the first edition of the Oxford English Dictionary in the mid 19th century and receives over 10,000 entries from a patient at Broadmoor Criminal Lunatic Asylum , Dr William Minor.\",\n" +
                "            \"release_date\": \"2019-03-07\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 1967,\n" +
                "            \"id\": 424783,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.4,\n" +
                "            \"title\": \"Bumblebee\",\n" +
                "            \"popularity\": 97.621,\n" +
                "            \"poster_path\": \"/fw02ONlDhrYjTSZV8XO6hhU3ds3.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Bumblebee\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                878\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/wtZj5nn6hVwgakPdg6y6gm3eFXU.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"On the run in the year 1987, Bumblebee finds refuge in a junkyard in a small Californian beach town. Charlie, on the cusp of turning 18 and trying to find her place in the world, discovers Bumblebee, battle-scarred and broken.  When Charlie revives him, she quickly learns this is no ordinary yellow VW bug.\",\n" +
                "            \"release_date\": \"2018-12-15\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 1257,\n" +
                "            \"id\": 504172,\n" +
                "            \"video\": true,\n" +
                "            \"vote_average\": 6.4,\n" +
                "            \"title\": \"The Mule\",\n" +
                "            \"popularity\": 77.378,\n" +
                "            \"poster_path\": \"/oeZh7yEz3PMnZLgBPhrafFHRbVz.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"The Mule\",\n" +
                "            \"genre_ids\": [\n" +
                "                80,\n" +
                "                18,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/bTeRgkAavyw1eCtSkaww18wLYNP.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Earl Stone, a man in his 80s, is broke, alone, and facing foreclosure of his business when he is offered a job that simply requires him to drive. Easy enough, but, unbeknownst to Earl, he’s just signed on as a drug courier for a Mexican cartel. He does so well that his cargo increases exponentially, and Earl hit the radar of hard-charging DEA agent Colin Bates.\",\n" +
                "            \"release_date\": \"2018-12-14\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 21,\n" +
                "            \"id\": 458253,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 7.6,\n" +
                "            \"title\": \"Missing Link\",\n" +
                "            \"popularity\": 74.298,\n" +
                "            \"poster_path\": \"/gEkKHiiQRVUSX15Iwo8VFydXrtu.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Missing Link\",\n" +
                "            \"genre_ids\": [\n" +
                "                16,\n" +
                "                35,\n" +
                "                12,\n" +
                "                14,\n" +
                "                10751\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/ob4XnKBqAh1rg381hayebJx6EkE.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"The charismatic Sir Lionel Frost considers himself to be the world's foremost investigator of myths and monsters. Trouble is, none of his small-minded, high-society peers seems to recognize this. Hoping to finally gain acceptance from these fellow adventurers, Sir Lionel travels to the Pacific Northwest to prove the existence of a legendary creature known as the missing link.\",\n" +
                "            \"release_date\": \"2019-04-04\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 127,\n" +
                "            \"id\": 454294,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6,\n" +
                "            \"title\": \"The Kid Who Would Be King\",\n" +
                "            \"popularity\": 68.751,\n" +
                "            \"poster_path\": \"/kBuvLX6zynQP0sjyqbXV4jNaZ4E.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"The Kid Who Would Be King\",\n" +
                "            \"genre_ids\": [\n" +
                "                28,\n" +
                "                12,\n" +
                "                14,\n" +
                "                10751\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/jmbgxBd86MshzQQvv5laKvOKoMm.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Old-school magic meets the modern world when young Alex stumbles upon the mythical sword Excalibur. He soon unites his friends and enemies, and they become knights who join forces with the legendary wizard Merlin. Together, they must save mankind from the wicked enchantress Morgana and her army of supernatural warriors.\",\n" +
                "            \"release_date\": \"2019-01-16\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 396,\n" +
                "            \"id\": 500682,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 6.7,\n" +
                "            \"title\": \"The Highwaymen\",\n" +
                "            \"popularity\": 64.237,\n" +
                "            \"poster_path\": \"/4bRYg4l12yDuJvAfqvUOPnBrxno.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"The Highwaymen\",\n" +
                "            \"genre_ids\": [\n" +
                "                80,\n" +
                "                18,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/pZ78ksjPlXf3q2EeONN8WdHE03Y.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"In 1934, Frank Hamer and Manny Gault, two former Texas Rangers, are commissioned to put an end to the wave of vicious crimes perpetrated by Bonnie Parker and Clyde Barrow, a notorious duo of infamous robbers and cold-blooded killers who nevertheless are worshiped by the public.\",\n" +
                "            \"release_date\": \"2019-03-15\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 98,\n" +
                "            \"id\": 471507,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 5.8,\n" +
                "            \"title\": \"Destroyer\",\n" +
                "            \"popularity\": 62.671,\n" +
                "            \"poster_path\": \"/sHw9gTdo43nJL82py0oaROkXXNr.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Destroyer\",\n" +
                "            \"genre_ids\": [\n" +
                "                53,\n" +
                "                80,\n" +
                "                18,\n" +
                "                28\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/rURV5xPzt9ZxEXAc4OQmxchGyZ8.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Erin Bell is an LAPD detective who, as a young cop, was placed undercover with a gang in the California desert with tragic results. When the leader of that gang re-emerges many years later, she must work her way back through the remaining members and into her own history with them to finally reckon with the demons that destroyed her past.\",\n" +
                "            \"release_date\": \"2018-12-25\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 62,\n" +
                "            \"id\": 480414,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 5.5,\n" +
                "            \"title\": \"The Curse of La Llorona\",\n" +
                "            \"popularity\": 61.364,\n" +
                "            \"poster_path\": \"/jhZlXSnFUpNiLAek9EkPrtLEWQI.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"The Curse of La Llorona\",\n" +
                "            \"genre_ids\": [\n" +
                "                53,\n" +
                "                27,\n" +
                "                9648\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/u2vGggeMPAkhEtD7bYGfeThsQiM.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"A social worker dealing with the disappearance of two children fears for her own family after beginning the investigation.\",\n" +
                "            \"release_date\": \"2019-04-17\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 6903,\n" +
                "            \"id\": 424694,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 8.1,\n" +
                "            \"title\": \"Bohemian Rhapsody\",\n" +
                "            \"popularity\": 60.8,\n" +
                "            \"poster_path\": \"/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Bohemian Rhapsody\",\n" +
                "            \"genre_ids\": [\n" +
                "                18,\n" +
                "                10402\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/xcaSYLBhmDzJ6P14bcKe0KTh3QV.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.\",\n" +
                "            \"release_date\": \"2018-10-24\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 60,\n" +
                "            \"id\": 449985,\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 5.6,\n" +
                "            \"title\": \"Triple Threat\",\n" +
                "            \"popularity\": 59.788,\n" +
                "            \"poster_path\": \"/cSpM3QxmoSLp4O1WAMQpUDcaB7R.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Triple Threat\",\n" +
                "            \"genre_ids\": [\n" +
                "                53,\n" +
                "                28,\n" +
                "                80\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/2f8fkWSWOVcTlWforoVpsuaCiwI.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"A crime syndicate places a hit on a billionaire's daughter, making her the target of an elite assassin squad. A small band of down-and-out mercenaries protects her, fighting tooth and nail to stop the assassins from reaching their target.\",\n" +
                "            \"release_date\": \"2019-03-19\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vote_count\": 264,\n" +
                "            \"id\": 157433,\n" +
                "            \"video\": true,\n" +
                "            \"vote_average\": 5.9,\n" +
                "            \"title\": \"Pet Sematary\",\n" +
                "            \"popularity\": 58.33,\n" +
                "            \"poster_path\": \"/7SPhr7Qj39vbnfF9O2qHRYaKHAL.jpg\",\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Pet Sematary\",\n" +
                "            \"genre_ids\": [\n" +
                "                27,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"backdrop_path\": \"/n2aX63BmW7zIKgKJ58e6rKlSsdi.jpg\",\n" +
                "            \"adult\": false,\n" +
                "            \"overview\": \"Louis Creed, his wife Rachel and their two children Gage and Ellie move to a rural home where they are welcomed and enlightened about the eerie 'Pet Sematary' located nearby. After the tragedy of their cat being killed by a truck, Louis resorts to burying it in the mysterious pet cemetery, which is definitely not as it seems, as it proves to the Creeds that sometimes dead is better.\",\n" +
                "            \"release_date\": \"2019-04-04\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"page\": 1,\n" +
                "    \"total_results\": 1088,\n" +
                "    \"dates\": {\n" +
                "        \"maximum\": \"2019-04-25\",\n" +
                "        \"minimum\": \"2019-03-08\"\n" +
                "    },\n" +
                "    \"total_pages\": 55\n" +
                "}"
    }
}