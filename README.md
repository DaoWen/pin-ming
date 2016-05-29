Pin-Ming
========

<strong>:warning: THIS SOFTWARE IS DEPRECATED :warning:</strong>

Please use [GedTools](https://github.com/DaoWen/gedtools) instead.

A brief history of Pin-Ming
---------------------------

I wrote this software at the request of Sister Sun Chia-Ling
at the LDS genealogy center in Kaohsiung Taiwan Stake.
They were spending countless hours annotating Chinese names
with Romanized versions so that they could be submitted for temple work.
I was shocked that such a monotonous task had not yet been automated!
She put me in contact with a brother in Taipei, who suggested using the
[Unihan database](http://unicode.org/charts/unihan.html)
for automatic Chinese-to-Pinyin (Romanized Chinese) conversion.
I chose the name "Pin-Ming" from the Chinese "拼名",
which can be directly translated as "spell name."

I delivered the first version of Pin-Ming to Siser Sun sometime in 2007.
It was used in LDS genealogy centers throughout Taiwan for about two years,
but I soon discovered that the Java dependence was a huge headache.
Even when my users got a JRE correctly installed,
they had problems getting the JAR files to execute.
This prompted me to start a new project, GedTools, written in C++.
I was able to bundle the GedTools binary and all of its DLL dependencies
in a 32-bit Windows installer, which made deployment much simpler.
GedTools also added new functionality,
including a full Traditional Chinese localization,
as well as a missing-date-estimation algorithm.

GedTools superseded Pin-Ming in 2009.
Basically, I'm just keeping this source code around for nostalgia's sake.
