## Document Summarization using TF/IDF Scores

1. `-compile` : compile all jar files
2. `-clean` : remove class files, remove jar file, remove output file
3. `-hadoop` : run hadoop job   
   `$2` desired output path




### Sample input


```
Jeannot Ahoussou-Kouadio<====>35087332<====>Jeannot Ahoussou-Kouadio Jeannot Ahoussou-Kouadio (born 6 March 1951 Official CV at government website  .  ) is an Ivorian politician who was Prime Minister of Ivory Coast from March 2012 to November 2012.  Previously he was Minister of Industry from 2002 to 2005 and Minister of Justice from 2010 to 2012. Ahoussou-Kouadio is a member of the Democratic Party of Ivory Coast – African Democratic Rally (PDCI–RDA), a party led by former President Henri Konan Bédié.  "Jeannot Ahoussou-Kouadio nommé Premier ministre en Côte d'Ivoire", Radio France Internationale, 13 March 2012  .   Since leaving office as Prime Minister, he has served as President of the Regional Council of Bélier Region  and as Minister of State at the Presidency for Political Dialogue and Relations with the Institutions. 

Mombum language<====>35087345<====>Mombum language Mombum, or Kemelom (Komolom), is a Trans–New Guinea language spoken on Yos Sudarso Island (Kolopom Island) in West New Guinea. References.

Bureau of Diplomatic Security bibliography<====>35087377<====>Bureau of Diplomatic Security bibliographyThis article is a bibliography of information for the Diplomatic Security Service, Bureau of Diplomatic Security. REWARDS FOR JUSTICE - DSS - Money for Information leading to the capture of TerroristsPamphlet - DSS: A Global Law Enforcement AgencyBBC article on DSSOFFICIAL U. S.  Diplomatic Security WebsiteU. S.  Diplomatic Security Photo GalleryU. S.  Diplomatic Security Pictorial HistoryU. S.  Diplomatic Security testifies before Senate's Homeland Security & Governmental Affairs Subcmte, SD-342U. S.  Diplomatic Security's Assistant Secretary of State testifies before the Senate C-SPANU. S.  Diplomatic Security's Assistant Secretary of State testifies before the Senate on 6/29/2011 C-SPANDS on C-SPANDiplomatic Security Special Agents AssociationDiplomatic Security - Office of Foreign MissionsDiplomatic Security WASHINGTON POST articleDiplomatic Security - Mobile Security Deployments (MSD)1996 Secretary of State Warren Christopher presents awards for valor to DSS Special Agents - TranscriptDS Special Agents at the OlympicsCBS Evening News - DSS at the UN General Assembly 2009CBS Evening News - Diplomatic Security Behind the ScenesCBS NEWS 6 June 2011 - Keeping U. 

Hydro-slotted perforation<====>35087381<====>Hydro-slotted perforation Hydro-slotting perforation technology is the process of opening the productive formation through the casing and cement sheath to produce the oil or gas product flow (intensification, stimulation).  The process has been used for industrial drilling since 1980, and involves the use of an underground hydraulic slotting engine (tool, equipment).  The technology helps to minimize compressive stress following drilling in the well-bore zone (which reduces the permeability in the zone).  Overview Since ancient times, when there were the first coal mines, it was observed, that increasing the depth of the development the coal tunnel, under the action of overburden pressure, surrounding rocks become harder and little-permeable. 
```





### Sample Output

Generate top 3 sentences with highest TF/IDF scores for each aticle.

```
35087332	  ) is an Ivorian politician who was Prime Minister of Ivory Coast from March 2012 to November 2012
35087332	  "Jeannot Ahoussou-Kouadio nommé Premier ministre en Côte d'Ivoire", Radio France Internationale, 13 March 2012  
35087332	 Ahoussou-Kouadio is a member of the Democratic Party of Ivory Coast – African Democratic Rally (PDCI–RDA), a party led by former President Henri Konan Bédié
35087345	Mombum language Mombum, or Kemelom (Komolom), is a Trans–New Guinea language spoken on Yos Sudarso Island (Kolopom Island) in West New Guinea
35087345	 References
35087377	  Diplomatic Security's Assistant Secretary of State testifies before the Senate C-SPANU
35087377	 S
35087377	  Diplomatic Security WebsiteU
35087381	Hydro-slotted perforation Hydro-slotting perforation technology is the process of opening the productive formation through the casing and cement sheath to produce the oil or gas product flow (intensification, stimulation)
35087381	  Overview Since ancient times, when there were the first coal mines, it was observed, that increasing the depth of the development the coal tunnel, under the action of overburden pressure, surrounding rocks become harder and little-permeable
35087381	  The process has been used for industrial drilling since 1980, and involves the use of an underground hydraulic slotting engine (tool, equipment)

```