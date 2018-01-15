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
    FlatList, ScrollView, TextInput, Button,Linking, AsyncStorage, Picker, TouchableOpacity

} from 'react-native';
import {StackNavigator, NavigationActions} from 'react-navigation'

import firebase from 'firebase'


export default class App extends Component {
  componentWillMount() {
      const firebaseConfig = {
          apiKey: "AIzaSyD3xgmLLyevaLYg_GZwMxhwYJwlcsS9rQE",
          authDomain: "musicrecordreact.firebaseapp.com",
          databaseURL: "https://musicrecordreact.firebaseio.com",
          projectId: "musicrecordreact",
          storageBucket: "musicrecordreact.appspot.com",
          messagingSenderId: "129809460259"
      };
      firebase.initializeApp(firebaseConfig);
      firebase.auth().signOut();
  }

  render() {
      return (<MyApplication/> )
  }
  componentDidMount(){
    this._updateList();
  }
  _updateList () { 
    // let response = AsyncStorage.getItem('listOfRecords').then((response) =>    { console.log(response); records=JSON.parse(response) || [];}); 
 
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
        </View>
    )
  }
}

class RecordList extends Component {
  constructor() {
        super();
        this.onRefresh = this.onRefresh.bind(this);
        this.records = []
        this.state = {
            refreshing: false,
        };
    }

    onRefresh() {
      this.setState({refreshing: true});
        firebase.database().ref().child("records").on('value', (childSnapshot) => {
                childSnapshot.forEach((doc) => {
                    let record = { title: doc.toJSON().title, band: doc.toJSON().band, id: doc.toJSON().id, year: doc.toJSON().year, genre: doc.toJSON().genre };
                    this.records.push(record);
                });
                this.setState({refreshing: false});
            });
            
    }

    componentWillMount() {
        this.onRefresh();
    }

  render(){
      const {navigate} = this.props.navigation;
      if (this.state.refreshing)
            return (
                <Text>Loading...</Text>
            );
    return(
        <View style={styles.container}>
        
          <Text style={{ fontSize:30}}> Records </Text>
          <FlatList
                updateCellsBatchingPeriod={2000}
                data = { this.records }
                renderItem={
                      ({item}) =>
                          <ScrollView>
                              <View style={styles.linearView} >
                                <TouchableOpacity onPress={() => navigate('Details',{ record : item })}> 
                                    <View>
                                    <Text style={styles.item} >{item.title} -> {item.band}</Text>
                                    </View>
                                  </TouchableOpacity>
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
    if (firebase.auth().currentUser.email === 'admin@gmail.com') {
    this.props
               .navigation
               .dispatch(NavigationActions.reset(
                 {
                    index: 0,
                    actions: [
                      NavigationActions.navigate({ routeName: 'AdminHomeScreen'})
                    ]
                  }));
             } else {
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
  }

  _updateRecord () { 
    if (this.state.band)
      record.band = this.state.band;
    if (this.state.year)
      record.year = this.state.year;
    if (this.state.genre)
      record.genre = this.state.genre;
    firebase.database().ref('records').child(record.id).update(record); 
  } 

  _deleteRecord () {
   firebase.database().ref('records').child(record.id).remove();
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
 
                 <TouchableOpacity onPress={() => {this._updateRecord(); this._resetStack(); }} >
                  <View style={styles.niceButton}>
                    <Text>Update Record</Text>
                    </View>
                    </TouchableOpacity>

                    <TouchableOpacity onPress={() => {this._deleteRecord(); this._resetStack(); }} >
                    <View style={styles.niceButton}>
                    <Text>Delete Record</Text>
                    </View>
                    </TouchableOpacity>

                    <TouchableOpacity onPress={() => navigate('RateRecord')}>
                    <View style={styles.niceButton}>
                    <Text>Rate Record</Text>
                    </View>
                    </TouchableOpacity>
            </View>
        </View>

        );
    }
}

class RateRecord extends Component{

  constructor(){
        super();
        this.state={
            newValue:"1",
        }
    }

   _resetStack() {
    if (firebase.auth().currentUser.email === 'admin@gmail.com') {
    this.props
               .navigation
               .dispatch(NavigationActions.reset(
                 {
                    index: 0,
                    actions: [
                      NavigationActions.navigate({ routeName: 'AdminHomeScreen'})
                    ]
                  }));
             } else {
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
  }

  _saveRecord () { 
    // console.log(record);
    // console.log(records);
   // for(var i=0;i<records.length;i++){
   //    if(records[i].title==record.title){
   //      console.log("hei " + records[i].title);
   //      records[i].votes.push(this.state.newValue);
     
   //    }
   // }

   //  AsyncStorage.setItem('listOfRecords',JSON.stringify(records)); 

  } 

  render(){
    const {navigate} = this.props.navigation;
    const {state} = this.props.navigation;
    record = state.params ? state.params.record : "<undefined>";
    return(
            <View style={styles.container}>
                <Text style={styles.title}> Rate Record: </Text>
                <Picker style={{width: 200}}
                 selectedValue={this.state.newValue}
                 onValueChange={(newValue) =>{ this.setState({newValue})}}>
                    <Picker.Item label="1" value="1" />
                    <Picker.Item label="2" value="2" />
                    <Picker.Item label="3" value="3" />
                    <Picker.Item label="4" value="4" />
                    <Picker.Item label="5" value="5" />
                </Picker>

                <View>
                    <TouchableOpacity onPress={() => {this._saveRecord(); this._resetStack(); }} >
                      <View style={styles.niceButton}>
                        <Text>Send Vote</Text>
                      </View>
                    </TouchableOpacity>
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
          <TouchableOpacity
              onPress={() => {
                                                subject = "Email sent from React Native";
                                                all = "mailto:" + this.state.email + "?subject=" + subject + "&body=" + this.state.content ;
                                                Linking.openURL(all)}} >
              <View style={styles.niceButton}>
              <Text>Send Email</Text>
              </View>
            </TouchableOpacity>
        </View>


      )
    }
}

class Add extends Component {
  // _addRecord () { 
  //   console.log("hello");
  //   records=[...records,this.state];
  //   console.log(records);
  //   console.log("hhh");

  //   AsyncStorage.setItem('listOfRecords',JSON.stringify(records)); 

  //   console.log(records);
  // } 
  render(){
          const {navigate} = this.props.navigation;

    return(
       <View style={styles.container}>

            <View  style={styles.singleItemDetails}>
              <TextInput onChangeText={(content)=>this.setState({title:content}) } style={styles.fullWidth} placeholder="Title" />
              <TextInput onChangeText={(content)=>this.setState({band:content}) } style={styles.fullWidth} placeholder="Band" />
              <TextInput onChangeText={(content)=>this.setState({year:content}) } style={styles.fullWidth} placeholder="Year" keyboardType="numeric"/>
              <TextInput onChangeText={(content)=>this.setState({genre:content}) } style={styles.fullWidth} placeholder="Genre" />
          <TouchableOpacity
              onPress={() => {
                            //this._addRecord(); 
                             let id = firebase.database().ref().child('records').push().key;
                             firebase.database().ref('records').child(id).update({
                                id: id,
                                title: this.state.title,
                                band: this.state.band,
                                year: this.state.year,
                                genre: this.state.genre
                            });
                            this.props
               .navigation.goBack(); }    
             } >
             <View style={styles.niceButton}>
              <Text>Add record</Text>
              </View>
            </TouchableOpacity>
            </View>
        </View>
      )
  }
}

class Login extends Component {

  constructor() {
    super();

    this.email = "admin@gmail.com";
    this.password = "123456";
  }

  render() {
    const {navigate} = this.props.navigation;
    return(
        <View style={styles.container}>
          <Text>LOGIN</Text>
          <TextInput
              style={{width: "80%", borderWidth: 1, backgroundColor: 'white'}}
              onChangeText={(email) => this.email = email}
              placeholder={"Email"} />
          <TextInput
              style={{width: "80%", borderWidth: 1, backgroundColor: 'white'}}
              onChangeText={(password) => this.password = password}
              secureTextEntry={true}
              placeholder={"Password"} />

              <TouchableOpacity
                onPress={() => {
                    console.log(this.email);
                    console.log(this.password);
                    firebase.auth().signInWithEmailAndPassword(this.email, this.password)
                        .then(function () {
                            alert("Welcome " + firebase.auth().currentUser.email + "!");
                            if (firebase.auth().currentUser.email === 'admin@gmail.com') {
                                navigate("AdminHomeScreen");
                            }
                            else {
                                navigate('Buttons');
                            }
                        }).catch(function (error) {
                        alert(error.code);
                        alert(error.message);
                    });
                }} >
                  <View style={styles.niceButton}>
                    <Text> Sign in </Text>
                  </View>
                </TouchableOpacity>
        </View>
      )
  }
}

class AdminHomeScreen extends Component {
  render() {
    const {navigate} = this.props.navigation;
    return (
       <View style={styles.container}>
         <TouchableOpacity onPress={() => navigate('Home')} >
          <View style={styles.niceButton}>
            <Text>See records</Text>
          </View>
         </TouchableOpacity>
          <TouchableOpacity onPress={() => { console.log("fmm"); navigate('AddScreen') }}>
            <View style={styles.niceButton}>
              <Text>Add Record</Text>
            </View>
          </TouchableOpacity>
          

        </View>
      )
  }
}

const MyApplication = StackNavigator({
    Login: {screen: Login},
    Buttons: {screen: Buttons},
    AdminHomeScreen: {screen: AdminHomeScreen},
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

    niceButton: {
      width: 70,
      height: 40,
      backgroundColor: "#eeeeee"
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