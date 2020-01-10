import React from 'react';
import { StyleSheet, View, 
  StatusBar, ListView, TextInput, TouchableOpacity} from 'react-native';

import firebase from 'firebase';

import { Container, Header, 
  Content, ActionSheet, 
  Text, Input, Icon, List, 
  ListItem, Item, Grid } from "native-base";

//import GetNewEntry from'./getNewEntry';
import Modal from 'react-native-modalbox';
import Button from 'react-native-button';

import DateTimePicker from 'react-native-modal-datetime-picker';

import Expo from 'expo';
import PresentItem from './PresentItem'
import  { createStackNavigator }  from 'react-navigation';

// Initialize Firebase
const firebaseConfig = {
    apiKey: "AIzaSyDoCtf1Jxn6L7xC6yyB_HhI8JbljsNoai4",
    authDomain: "corporita-app.firebaseapp.com",
    databaseURL: "https://corporita-app.firebaseio.com",
    projectId: "corporita-app",
    storageBucket: "corporita-app.appspot.com",
    messagingSenderId: "916986105467"
  };
  
  firebase.initializeApp(firebaseConfig);
  
  var data = []
  var dataDisc = []
  var firebasePriority = '/Priority1'
  
  export default class PriorityMatrix extends React.Component {
  
    constructor(props) {
      super(props);
      this.ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 })
      this.state = {
        listViewData1: data,
        listViewData2: data,
        listViewData3: data,
        listViewData4: data,

        toggle1: false,
        toggle2: false,
        toggle3: false,
        toggle4: false,

        pressToClose : false,

        newContact: "",
        newDiscription : "",
        newDate : "",
        isDateTimePickerVisible: false
      }
  
    }
  
    componentDidMount() {
      var that = this;
      firebase.database().ref('/Priority1').on('child_added', function (data) {
        var newData = [...that.state.listViewData1]
        newData.push(data)
        that.setState({ listViewData1: newData })
      })
      firebase.database().ref('/Priority2').on('child_added', function (data) {
        var newData = [...that.state.listViewData2]
        newData.push(data)
        that.setState({ listViewData2: newData })
      })
      firebase.database().ref('/Priority3').on('child_added', function (data) {
        var newData = [...that.state.listViewData3]
        newData.push(data)
        that.setState({ listViewData3: newData })
      })
      firebase.database().ref('/Priority4').on('child_added', function (data) {
        var newData = [...that.state.listViewData4]
        newData.push(data)
        that.setState({ listViewData4: newData })
      })
    }
  
    addRow(data) {
      var key = firebase.database().ref(firebasePriority).push().key
      firebase.database().ref(firebasePriority).child(key).set({ name: data })
    }
    
    addDiscription(dataDisc, data, date_data) {
        var key = firebase.database().ref(firebasePriority).push().key
        firebase.database().ref(firebasePriority).child(key).set({ name : data,  discription : dataDisc, date : date_data})//Saves both the name and the description together
    }
  
    async deleteRow1(secId, rowId, rowMap, data) {
      await firebase.database().ref('/Priority1' + data.key + dataDisc.key).set(null)
      rowMap[`${secId}${rowId}`].props.closeRow();
      var newData = [...this.state.listViewData1];
      newData.splice(rowId, 1)
      this.setState({ listViewData1: newData });
    }
    async deleteRow2(secId, rowId, rowMap, data) {
      await firebase.database().ref('/Priority2' + data.key + dataDisc.key).set(null)
      rowMap[`${secId}${rowId}`].props.closeRow();
      var newData = [...this.state.listViewData2];
      newData.splice(rowId, 1)
      this.setState({ listViewData2: newData });
    }
    async deleteRow3(secId, rowId, rowMap, data) {
      await firebase.database().ref('/Priority3' + data.key + dataDisc.key).set(null)
      rowMap[`${secId}${rowId}`].props.closeRow();
      var newData = [...this.state.listViewData3];
      newData.splice(rowId, 1)
      this.setState({ listViewData3: newData });
    }
    async deleteRow4(secId, rowId, rowMap, data) {
      await firebase.database().ref('/Priority4' + data.key + dataDisc.key).set(null)
      rowMap[`${secId}${rowId}`].props.closeRow();
      var newData = [...this.state.listViewData4];
      newData.splice(rowId, 1)
      this.setState({ listViewData4: newData });
    }

    onClose() {
      console.log('Modal just closed');
    }
    onOpen() {
      console.log('Modal just opened');
    }
    showInformation() {
  
    }

    _OnPress1() {
      const newState = !this.state.toggle1;
      this.setState({toggle1: newState})
      this.setState({toggle2: false})
      this.setState({toggle3: false})
      this.setState({toggle4: false})
    }
    _OnPress2() {
      const newState = !this.state.toggle2;
      this.setState({toggle2: newState})
      this.setState({toggle1: false})
      this.setState({toggle3: false})
      this.setState({toggle4: false})    }
    _OnPress3() {
      const newState = !this.state.toggle3;
      this.setState({toggle3: newState})
      this.setState({toggle1: false})
      this.setState({toggle2: false})
      this.setState({toggle4: false})    }
    _OnPress4() {
      const newState = !this.state.toggle4;
      this.setState({toggle4: newState})
      this.setState({toggle1: false})
      this.setState({toggle2: false})
      this.setState({toggle3: false})    }

    OpenItemActivity (name_Data, disc_data, date_data)
    {
       this.props.navigation.navigate("PresentItem", { Name: name_Data, Discription: disc_data, Date: date_data});
    }

    //Date time picker functions
  _showDateTimePicker = () => this.setState({ isDateTimePickerVisible : true });

  _hideDateTimePicker = () => this.setState({ isDateTimePickerVisible: false });

   _handleDatePicked = (date) => {
     console.log('A date has been picked: ', date);
    this.setState({newDate : date.toString()});
     this._hideDateTimePicker();
   };


    render() {

      const {toggle1} = this.state;
      const textColor1 = toggle1? "black" : "white";
      const button1Color = toggle1? "white" : "#ff1a1a";

      const {toggle2} = this.state;
      const textColor2 = toggle2? "black" : "white";
      const button2Color = toggle2?  "white" : "#ff8000";

      const {toggle3} = this.state;
      const textColor3 = toggle3? "black" : "white";
      const button3Color = toggle3? "white" : "#ffff00";

      const {toggle4} = this.state;
      const textColor4 = toggle4?  "black" : "white";
      const button4Color = toggle4?  "white" : "#808080";

      return (
        <Container>
          <Header style={{ marginTop: StatusBar.currentHeight, 
            borderRadius: 10,
            overflow: 'hidden',}}>
            <Button onPress={() => this.refs.modal1.open()} style={styles.btn}>Add Event</Button>  
          </Header>
        <Grid>
            <Content>
                <Header style={styles.importantUrgent}>
                  <Text style = {styles.textProp}> Important Urgent </Text>
                </Header>
              <List
                enableEmptySections
                dataSource={this.ds.cloneWithRows(this.state.listViewData1)}
                renderRow={data =>
                  <ListItem button onPress= {this.OpenItemActivity.bind(this, data.val().name, data.val().discription, data.val().date)}>
                    <Text> {data.val().name}</Text>
                  </ListItem>
                }
                renderLeftHiddenRow={data =>
                  <Button full onPress={() => this.addRow(data)} >
                    <Icon name="information-circle" />
                  </Button>
                }
                renderRightHiddenRow={(data, secId, rowId, rowMap) =>
                  <Button full danger onPress={() => this.deleteRow1(secId, rowId, rowMap, data)}>
                    <Icon name="trash" />
                  </Button>
    
                }
                leftOpenValue={-75}
                rightOpenValue={-75}
              />
              </Content>
          <Content>
            <Header style={styles.importantNotUrgent}>
                  <Text style = {styles.textProp}> Important Not Urgent </Text>
            </Header>
            <List
              enableEmptySections
              dataSource={this.ds.cloneWithRows(this.state.listViewData2)}
              renderRow={data =>
                <ListItem>
                  <Text> {data.val().name}</Text>
                </ListItem>
              }
              renderLeftHiddenRow={data =>
                <Button full onPress={() => this.addRow(data)} >
                  <Icon name="information-circle" />
                </Button>
              }
              renderRightHiddenRow={(data, secId, rowId, rowMap) =>
                <Button full danger onPress={() => this.deleteRow2(secId, rowId, rowMap, data)}>
                  <Icon name="trash" />
                </Button>
  
              }
              leftOpenValue={-75}
              rightOpenValue={-75}
            />
            </Content>
          </Grid>
          <Grid>
            <Content>
                <Header style={styles.notImportantUregnt}>
                  <Text style = {styles.textProp}> Not Important Urgent </Text>
                </Header>
              <List
                enableEmptySections
                dataSource={this.ds.cloneWithRows(this.state.listViewData3)}
                renderRow={data =>
                  <ListItem>
                    <Text> {data.val().name}</Text>
                  </ListItem>
                }
                renderLeftHiddenRow={data =>
                  <Button full onPress={() => this.addRow(data)} >
                    <Icon name="information-circle" />
                  </Button>
                }
                renderRightHiddenRow={(data, secId, rowId, rowMap) =>
                  <Button full danger onPress={() => this.deleteRow3(secId, rowId, rowMap, data)}>
                    <Icon name="trash" />
                  </Button>
    
                }
                leftOpenValue={-75}
                rightOpenValue={-75}
              />
              </Content>
          <Content>
            <Header style={styles.notImportantNotUrgent}>
                  <Text style = {styles.textProp}> Not Important Not Urgent </Text>
            </Header>
            <List
              enableEmptySections
              dataSource={this.ds.cloneWithRows(this.state.listViewData4)}
              renderRow={data =>
                <ListItem>
                  <Text> {data.val().name}</Text>
                </ListItem>
              }
              renderLeftHiddenRow={data =>
                <Button full onPress={() => this.addRow(data)} >
                  <Icon name="information-circle" />
                </Button>
              }
              renderRightHiddenRow={(data, secId, rowId, rowMap) =>
                <Button full danger onPress={() => this.deleteRow4(secId, rowId, rowMap, data)}>
                  <Icon name="trash" />
                </Button>
  
              }
              leftOpenValue={-75}
              rightOpenValue={-75}
            />
            </Content>
          </Grid>

        <Modal
          style={[styles.modal, styles.modal1]}
          ref={"modal1"}
          //backdropPressToClose = {this.state.backdropPressToClose}
          //swipeToClose={this.state.swipeToClose}
          backButtonClose = {this.state.pressToClose}
          onClosed={this.onClose}
          onOpened={this.onOpen}
          onClosingState={this.onClosingState}>
            <Text style={styles.text}>Enter Your Task</Text>
            <Item style = {{margin : 20}}>
            <Input
              style = {styles.input}
              onChangeText={(newContact) => this.setState({ newContact })}
              placeholder="Add Item Name"
            />
            </Item>
            <Button 
            onPress = {() => {this._OnPress1(); firebasePriority = '/Priority1'}}
            style = {{color: textColor1, 
                backgroundColor: button1Color,
                alignItems: 'center',
                justifyContent : 'center',
                textAlign: 'center',
                margin: 5,
                padding : 10,
                borderColor: '#ff1a1a',
                borderWidth: 2,
                borderRadius: 10,
                overflow: 'hidden',}}> 
            Important Urgent</Button>

            <Button 
            onPress = {() => {this._OnPress2(); firebasePriority = '/Priority2'}}
            style = {{color: textColor2, 
            backgroundColor: button2Color,
            alignItems: 'center',
            justifyContent : 'center',
            textAlign: 'center',
            margin: 5,
            padding : 10,
            borderColor: '#ff8000',
            borderWidth: 2,
            borderRadius: 10,
            overflow: 'hidden',}}> 
            Important Not Urgent</Button>
            
            <Button 
            onPress = {() => {this._OnPress3(); firebasePriority = '/Priority3'}}
            style = {{color: textColor3, 
            backgroundColor: button3Color,
            alignItems: 'center',
            justifyContent : 'center',
            textAlign: 'center',
            margin: 5,
            padding : 10,
            borderColor: '#ffff00',
            borderWidth: 2,
            borderRadius: 10,
            overflow: 'hidden',}}> 
            Not Important Urgent</Button>

            <Button 
            onPress = {() => {this._OnPress4(); firebasePriority = '/Priority4'}}
            style = {{color: textColor4, 
            backgroundColor: button4Color,
            alignItems: 'center',
            justifyContent : 'center',
            textAlign: 'center',
            margin: 5,
            padding : 10,
            borderColor: '#808080',
            borderWidth: 2,
            borderRadius: 10,
            overflow: 'hidden',}}> 
            Not Important Not Urgent</Button>
            
            <Grid style ={{margin: 20}}>
            <Button onPress={() => {this.addRow(this.state.newContact), this.setState({visibleModal: false})}}
            style = {{color: 'white', 
            backgroundColor: "#3B5998",
            alignItems: 'center',
            justifyContent : 'center',
            textAlign: 'center',
            padding : 10,
            margin : 15,
            borderColor: "#3B5998",
            borderWidth: 2,
            borderRadius: 10,
            overflow: 'hidden',}}>
            Add Task
            </Button>

            <Button onPress={() => { this.refs.modalAdd.open()}}
            style = {{color: 'white', 
            backgroundColor: '#808080',
            alignItems: 'center',
            justifyContent : 'center',
            textAlign: 'center',
            padding : 10,
            margin : 15,
            borderColor: '#808080',
            borderWidth: 2,
            borderRadius: 10,
            overflow: 'hidden',}}>
            More Options
            </Button>
            </Grid>
        </Modal>

        <Modal
          style={[styles.modal, styles.modal1]}
          ref={"modalAdd"}
          swipeToClose={this.state.swipeToClose}
          onClosed={this.onClose}
          onOpened={this.onOpen}
          onClosingState={this.onClosingState}>
          <Text style={styles.text}>More Options</Text>
            <Item style = {{margin : 20}}>
            <Input
              style = {styles.inputMoreOptions}
              onChangeText={(newDiscription) => this.setState({ newDiscription })}
              multiline = {true}
              numberOfLines = {5}
              placeholder="Add Item Description"
            />
            </Item>
            <Button onPress={() => this.setState({ isDateTimePickerVisible : true })}
            style = {{color: 'white', 
            backgroundColor: '#808080',
            alignItems: 'center',
            justifyContent : 'center',
            textAlign: 'center',
            padding : 10,
            margin : 15,
            borderColor: '#808080',
            borderWidth: 2,
            borderRadius: 10,
            overflow: 'hidden',}}>
            Add Date
            </Button>
            <DateTimePicker
                mode = {"datetime"}
                isVisible = {this.state.isDateTimePickerVisible}
                onConfirm={this._handleDatePicked}
                onCancel={this._hideDateTimePicker}
              />

            <Button onPress={() => {this.addDiscription(this.state.newDiscription, this.state.newContact, this.state.newDate)}}
            style = {{color: 'white', 
            backgroundColor: '#808080',
            alignItems: 'center',
            justifyContent : 'center',
            textAlign: 'center',
            padding : 10,
            margin : 15,
            borderColor: '#808080',
            borderWidth: 2,
            borderRadius: 10,
            overflow: 'hidden',}}>
            Finish
            </Button>
        </Modal>
        </Container>
      );
    }
  }
  
  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#635DB7',
      margin : 0,
      flexDirection : 'row',  
    },
    importantUrgent : {
      flex : 1,
      backgroundColor : "#ff1a1a",
    },
    importantNotUrgent : {
      flex : 1,
      backgroundColor : "#ff8000",
    },
    notImportantUregnt : {
      flex : 1,
      backgroundColor : "#ffff00",
    },
    notImportantNotUrgent : {
      flex : 1,
      backgroundColor : "#808080",
    },
    textProp : {
      fontSize: 15,
      color : 'white',
    },
    btn: {
      flex: 1,
      margin: 5,
      backgroundColor: "#3B5998",
      color: "white",
      padding: 10,
      alignItems: 'center',
      justifyContent : 'center',
      textAlign: 'center',
      borderRadius: 10,
    },
    modal: {
      justifyContent: 'center',
      alignItems: 'center'
    },
    input : {
      textAlign: 'center',
      fontSize: 18 
    },
    inputMoreOptions : {
      textAlign: 'center',
      fontSize: 18,
      height: 200
    },
    text: {
      color: "black",
      fontWeight: 'bold',
      fontSize: 22
    },
  });

