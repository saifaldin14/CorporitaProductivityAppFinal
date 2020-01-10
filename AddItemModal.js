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

import Expo from 'expo';
import  { createStackNavigator }  from 'react-navigation';


var firebasePriority = '/Priority1'

class AddItemModal extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          toggle1: false,
          toggle2: false,
          toggle3: false,
          toggle4: false,
  
          newContact: ""
        }
    
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

        return(
            <Container
          style={[styles.modal, styles.modal1]}
          ref={"modal1"}
          swipeToClose={this.state.swipeToClose}
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
            <Button onPress={() => this.addRow(this.state.newContact)}
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

            <Button onPress={() => this.refs.modal1.open()}
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
        </Container>
        );
    }
}
export default AddItemModal;

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#635DB7',
      margin : 0,
      flexDirection : 'row',  
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
    text: {
      color: "black",
      fontWeight: 'bold',
      fontSize: 22
    },
  });