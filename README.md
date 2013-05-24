Transparent Government - Transcript
================================

General Information
-------------------

 * Speech begins at 17:17 in the audio file
 * Speech begins on line 85 of the transcript file
 * Correction types
    * `phrase_change` - minor or major word changes
    * `grammar` - capitalization, etc
    * `punct` - adding or deleting punctuation

Instructions
------------
There's not right or wrong way to do this, so I'll just explain my workflow:

 * Have a media player open, playing the audio file.
 * Have a text editor open with the transcript.
 * Play the audio file and follow along in the transcript.  Anytime you see an error, make a correction with the following format:
 
        <choice>
            <sic>center hancock</sic>
            <corr type="">Senator Hancock</corr>
        </choice>

 * This should be placed immediately after the last correct word.  Whatever you are changing should be placed in the `<sic>` tag, and your correction should be placed inside the `<corr>` tag.
 * You can leave out the type for now, but that is where you would indicate the type of corrections you made (from the correction types above).
 * The text should read correctly by reading what comes before the `<choice>` tag, then what's inside the `<corr>` tag, then what comes after the closing `</choice>` tag.
 
 * Example:
     * What was actually said:
         
            File item number five, SB-1070, by Senator Steinberg.  File item number six, SB-1088, by Senator Price.     

     * after corrections:

            <SYNC Start="1063340">
                <P Class="ENUSCC" ID="Source">[Automatic Transcript Recognizability 82%]</P>
                <P Class="ENUSCC">by
                <choice>
                    <sic>steinberg file</sic>
	                  <corr type="grammar, punct, phrase_change">Senator Steinberg.  File</corr>
                </choice>
                item number six</P>
            </SYNC>
            
     * before corrections (outputted by MAVIS):
     
             <SYNC Start="1063340">
                <P Class="ENUSCC" ID="Source">[Automatic Transcript Recognizability 82%]</P>
                <P Class="ENUSCC">by steinberg file item number six</P>
            </SYNC>
            
 * Author attribution
     * We just talked about this yesterday, and decided that we would add an `author_last` and `author_first` attribute to utterances, but only where the speaker changes.  This way, we're not labeling every utterance.
     * Example:
     
            <SYNC Start="1063340" author_last="Smith" author_first="John">
                <P Class="ENUSCC" ID="Source">[Automatic Transcript Recognizability 82%]</P>
                <P Class="ENUSCC">by
                <choice>
                    <sic>steinberg file</sic>
                    <corr type="grammar, punct, phrase_change">Senator Steinberg.  File</corr>
                </choice>
                item number six</P>
            </SYNC>

            ... same speaker says more stuff, don't add author attributes to these utterances...
            
            ... now new speaker starts saying stuff, add attribute to indicate speaker changes...
            
            <SYNC Start="1102470" author_last="Schmoe" author_first="Joe">
                <P Class="ENUSCC" ID="Source">[Automatic Transcript Recognizability 83%]</P>
                <P Class="ENUSCC">
                <choice>
                    <sic>mister</sic>
	                  <corr type="phrase_change">Mr. Pedia's</corr>
                </choice>
                bill which is
                <choice>
	                  <sic>SB fifteen thirty</sic>
	                  <corr>SB-1530</corr>
                </choice>
               and we will </P>
            </SYNC>
            
    * Unfortunately, I am sure there will be overlap between utterances and speakers (more than 1 speaker in a single utterance).  In this case, just assign the speaker who speaks the majority of the utterance.

Important Notes
---------------
This is how we can break up who does what.  I'll do the first hour, and Giovanni, you can do the second hour.  I have broken it up right as a new speaker begins.

 * Bradley: 00:00:00 - 1:08:18
 * Giovanni: 1:08:18 - 2:07:00 (this begins at line 4505)
