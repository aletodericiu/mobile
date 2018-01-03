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
    FlatList, ScrollView, TextInput, Button,Linking, AsyncStorage, Picker

} from 'react-native';
import {StackNavigator, NavigationActions} from 'react-navigation'


var records=[]




export default class App extends Component {
  render() {
      return (<MyApplication/> )
  }
  componentDidMount(){
    this._updateList();
  }
  _updateList () { 
    let response = AsyncStorage.getItem('listOfRecords').then((response) =>    { console.log(response); records=JSON.parse(response) || [];}); 
 
  } 
}


class Buttons extends Component{
    render(){
      const {navigate} = this.props.navigation;
    return(
        <View style={styles.container}>
         <Button onPress={() => navigate('Home')}
                    title="See records"
                    color="#888888"  />
          <Button onPress={() => navigate('AddScreen')}
                    title="Add record"
                    color="#888888"  />
        </View>
    )
  }
}

class RecordList extends Component {

  render(){
      const {navigate} = this.props.navigation;
    return(
        <View style={styles.container}>
        
          <Text style={{ fontSize:30}}> Records </Text>
          <FlatList
                updateCellsBatchingPeriod={2000}
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
  // var record;

  _resetStack() {
    this.props
               .navigation
               .dispatch(NavigationActions.reset(
                 {
                    index: 0,
                    actions: [
                      NavigationActions.navigate({ routeName: 'Buttons'})
                    ]
                  }));
  }

  _updateRecord () { 
    console.log(record);
    console.log(records);
   for(var i=0;i<records.length;i++){
      if(records[i].title==record.title){
        console.log("hei " + records[i].title);
        records[i].band = this.state.band ? this.state.band : record.band;
        records[i].year = this.state.year ? this.state.year : record.year;
        records[i].genre = this.state.genre ? this.state.genre : record.genre;
      }
   }

    AsyncStorage.setItem('listOfRecords',JSON.stringify(records)); 

  } 

  _deleteRecord () {
    for(var i=0;i<records.length;i++){
      if(records[i].title==record.title){
        records.splice(i, 1);
        break;
      }
   }
        AsyncStorage.setItem('listOfRecords',JSON.stringify(records)); 

  }

    render(){
        const {navigate} = this.props.navigation;
        const {state} = this.props.navigation;
        console.log(state);
        record = state.params ? state.params.record : "<undefined>";
        //this.setState({this.state.votes.append(): itemValue})
        return(
        <View style={styles.container}>

            <View  style={styles.singleItemDetails}>
              <Text>{record.title}</Text>
              <TextInput onChangeText={(content)=>this.setState({band:content})}>{record.band}</TextInput>
              <TextInput onChangeText={(content)=>this.setState({year:content})}>{record.year}</TextInput>
              <TextInput onChangeText={(content)=>this.setState({genre:content})}>{record.genre}</TextInput>
 
                 <Button onPress={() => {this._updateRecord(); this._resetStack(); }}

                    title="Update Record"
                    color="#888888"  />

                    <Button onPress={() => {this._deleteRecord(); this._resetStack(); }}

                    title="Delete Record"
                    color="#888888"  />

                    <Button onPress={() => navigate('RateRecord')}
                    title="Rate Record"
                    color="#888888"  />
            </View>
        </View>

        );
    }
}

class RateRecord extends Component{

  constructor(){
        this.state={
            newValue:1,
        }
    }

    _resetStack() {
    this.props
               .navigation
               .dispatch(NavigationActions.reset(
                 {
                    index: 0,
                    actions: [
                      NavigationActions.navigate({ routeName: 'Buttons'})
                    ]
                  }));
  }

  _saveRecord () { 
    console.log(record);
    console.log(records);
   for(var i=0;i<records.length;i++){
      if(records[i].title==record.title){
        console.log("hei " + records[i].title);
        records[i].votes.push(this.state.newValue);
     
      }
   }

    AsyncStorage.setItem('listOfRecords',JSON.stringify(records)); 

  } 

  render(){
    const {navigate} = this.props.navigation;
    const {state} = this.props.navigation;
    record = state.params ? state.params.record : "<undefined>";
    return(
            <View style={styles.container}>
                <Text style={styles.title}> Rate Record: </Text>
                <Picker
                 selectedValue={this.state.newValue}
                 onValueChange={(newValue) =>{ this.setState({newValue})}}>
                    <Picker.Item label="1" value="1" />
                    <Picker.Item label="2" value="2" />
                    <Picker.Item label="3" value="3" />
                    <Picker.Item label="4" value="4" />
                    <Picker.Item label="5" value="5" />
                </Picker>

                <View>
                    <Button onPress={() => {this._saveRecord(); this._resetStack(); }}

                    title="Send Vote"
                    color="#888888"  />
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

class Add extends Component {
  _addRecord () { 
    console.log("hello");
    records=[...records,this.state];
    console.log(records);
    console.log("hhh");

    AsyncStorage.setItem('listOfRecords',JSON.stringify(records)); 

    console.log(records);
  } 
  render(){
          const {navigate} = this.props.navigation;

    return(
       <View style={styles.container}>

            <View  style={styles.singleItemDetails}>
              <TextInput onChangeText={(content)=>this.setState({title:content}) } style={styles.fullWidth} placeholder="Title" />
              <TextInput onChangeText={(content)=>this.setState({band:content}) } style={styles.fullWidth} placeholder="Band" />
              <TextInput onChangeText={(content)=>this.setState({year:content}) } style={styles.fullWidth} placeholder="Year" keyboardType="numeric"/>
              <TextInput onChangeText={(content)=>this.setState({genre:content}) } style={styles.fullWidth} placeholder="Genre" />
          <Button
              onPress={() => {
                            this._addRecord(); this.props
               .navigation
               .dispatch(NavigationActions.reset(
                 {
                    index: 0,
                    actions: [
                      NavigationActions.navigate({ routeName: 'Buttons'})
                    ]
                  })); }    }      
              title="Add record"
              color="#888888"
          />
            </View>
        </View>
      )
  }
}

const MyApplication = StackNavigator({
    Buttons: {screen: Buttons},
    Home: {screen: RecordList},
    Email: {screen: EmailComponent},
    Details: {screen: Details},
    AddScreen: {screen: Add},
    RateRecord: {screen: RateRecord}
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

    title:{
        fontSize: 30,
        alignSelf: 'center',
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