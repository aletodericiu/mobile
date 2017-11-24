/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
    AppRegistry,
    Platform,
    StyleSheet,
    Text,
    View,
    FlatList, ScrollView, TextInput, Button,Linking

} from 'react-native';
import {StackNavigator} from 'react-navigation'


var records=[
    {
      key:'1',
        title:'song1',
        band:'band1',
        year:'1990',
        genre:'genre1'
    },
    {
        key:'2',
        title:'song2',
        band:'band2',
        year:'2000',
        genre:'genre2'
    },
    {
        key:'3',
        title:'song3',
        band:'band3',
        year:'2010',
        genre:'genre3'
    }
]



export default class App extends Component {
  render() {
      return (<MyApplication/> )
  }
}


class RecordList extends Component {

  render(){
      const {navigate} = this.props.navigation;
    return(
        <View style={styles.container}>
          <Text style={{ fontSize:30}}> Records </Text>
          <FlatList
                data = { records }
                renderItem={
                      ({item}) =>
                          <ScrollView>
                              <View style={styles.linearView} >
                                <Text style={styles.item} onPress={() => navigate('Details',{ record : item })
                                                                        } >{item.title} -> {item.band}</Text>
                              </View>
                          </ScrollView>
                      } />

          <View >
            <Button onPress={() => navigate('Email')}
                    title="Send Email"
                    color="#888888"  />
           
           </View>
        </View>
    )
  }

}


class Details extends Component{

    render(){
        const {state} = this.props.navigation;
        var record = state.params ? state.params.record : "<undefined>";
        return(
        <View style={styles.container}>

            <View  style={styles.singleItemDetails}>
              <Text>{record.title}</Text>
              <TextInput> {record.band} </TextInput>
              <TextInput> {record.year} </TextInput>
              <TextInput> {record.genre} </TextInput>

              <ScrollView>
                     <TextInput style={{height: 100, width: 300, marginTop:10 }} multiline={true}> {record.storyline} </TextInput>
              </ScrollView>
            </View>
        </View>

        );
    }
}


class EmailComponent extends Component{

    render(){
      return(

        <View style={styles.container}>
          <Text> Email </Text>
          <TextInput onChangeText={(email)=>this.setState({email})} style={styles.fullWidth} />
          <Text> Content </Text>
          <TextInput onChangeText={(content)=>this.setState({content}) } style={styles.fullWidth} />
          <Button
              onPress={() => {
                                                subject = "Email sent from React Native";
                                                all = "mailto:" + this.state.email + "?subject=" + subject + "&body=" + this.state.content ;
                                                Linking.openURL(all)}}
              title="Send Email"
              color="#888888"
          />
        </View>


      )
    }
}

const MyApplication = StackNavigator({
    Home: {screen: RecordList},
    Email: {screen: EmailComponent},
    Details: {screen: Details}
})

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#D4D4D4',
  },


    singleItemDetails:{
        marginTop:20
    },

    item: {

            padding: 10,
            fontSize: 18,
            height: 44,
        },

    linearView: {
            flexDirection:'row',
            padding:10,
        },
   recordList:{
    marginTop:50,
       marginBottom: 50,
       backgroundColor: '#F91111',

   },

  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },

  fullWidth: {
    width: 300,
  },
});