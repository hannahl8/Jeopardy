// src/components/GameFormItem.tsx
import {object, string, array, number, ValidationError} from 'yup';
import {Form, Formik, FieldArray, FormikHelpers} from 'formik';
import TextInput from './form/TextInput';
import CategoryFormItem from './CategoryFormItem';
import {CategoryRequest, ClueRequest, GameDetails, GameRequest, ServerErrorResponse} from '../types';
import {sleep} from "../utils.ts";
import {useState} from "react";
import {createGame} from "../services.ts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSpinner} from "@fortawesome/free-solid-svg-icons";
import "./GameFormItem.css"

// const initialValues: GameRequest = {
//     name: '',
//     categoryRequests: Array(6).fill({
//         name: '',
//         clueRequests: [
//             {question: '', answer: '', value: 100},
//             {question: '', answer: '', value: 200},
//             {question: '', answer: '', value: 300},
//             {question: '', answer: '', value: 400},
//             {question: '', answer: '', value: 500}
//         ]
//     })
// };

// For testing Purposes
const initialValues: GameRequest = {
    name: 'Game 1',
    categoryRequests: [
        {
            name: 'Category 1',
            clueRequests: [
                {question: 'question 1', answer: 'answer 1', value: 100},
                {question: 'question 2', answer: 'answer 2', value: 200},
                {question: 'question 3', answer: 'answer 3', value: 300},
                {question: 'question 4', answer: 'answer 4', value: 400},
                {question: 'question 5', answer: 'answer 5', value: 500}
            ]
        },
        {
            name: 'Category 2',
            clueRequests: [
                {question: 'question 1', answer: 'answer 1', value: 100},
                {question: 'question 2', answer: 'answer 2', value: 200},
                {question: 'question 3', answer: 'answer 3', value: 300},
                {question: 'question 4', answer: 'answer 4', value: 400},
                {question: 'question 5', answer: 'answer 5', value: 500}
            ]
        },
        {
            name: 'Category 3',
            clueRequests: [
                {question: 'question 1', answer: 'answer 1', value: 100},
                {question: 'question 2', answer: 'answer 2', value: 200},
                {question: 'question 3', answer: 'answer 3', value: 300},
                {question: 'question 4', answer: 'answer 4', value: 400},
                {question: 'question 5', answer: 'answer 5', value: 500}
            ]
        },
        {
            name: 'Category 4',
            clueRequests: [
                {question: 'question 1', answer: 'answer 1', value: 100},
                {question: 'question 2', answer: 'answer 2', value: 200},
                {question: 'question 3', answer: 'answer 3', value: 300},
                {question: 'question 4', answer: 'answer 4', value: 400},
                {question: 'question 5', answer: 'answer 5', value: 500}
            ]
        },
        {
            name: 'Category 5',
            clueRequests: [
                {question: 'question 1', answer: 'answer 1', value: 100},
                {question: 'question 2', answer: 'answer 2', value: 200},
                {question: 'question 3', answer: 'answer 3', value: 300},
                {question: 'question 4', answer: 'answer 4', value: 400},
                {question: 'question 5', answer: 'answer 5', value: 500}
            ]
        },
        {
            name: 'Category 6',
            clueRequests: [
                {question: 'question 1', answer: 'answer 1', value: 100},
                {question: 'question 2', answer: 'answer 2', value: 200},
                {question: 'question 3', answer: 'answer 3', value: 300},
                {question: 'question 4', answer: 'answer 4', value: 400},
                {question: 'question 5', answer: 'answer 5', value: 500}
            ]
        },
    ]
};

const validationSchema = object<GameRequest>({
    name: string()
        .required('Please provide a game name.')
        .max(45, 'Game name can have at most 45 characters.'),
    categoryRequests: array().of(
        object<CategoryRequest>({
            name: string()
                .required('Please provide a category name.')
                .max(45, 'Category name can have at most 45 characters.'),
            clueRequests: array().of(
                object<ClueRequest>({
                    question: string()
                        .required('Please provide a question.')
                        .max(255, 'Question can have at most 255 characters.'),
                    answer: string()
                        .required('Please provide an answer.')
                        .max(255, 'Question can have at most 255 characters.'),
                    value: number()
                        .required('Please provide a value.')
                        .min(100, "Value must be at least 100.")
                        .max(1000, "Value can be at most 1000.")
                })
            )
        })
    )
});

function GameFormItem() {

    const [submitStatus, setSubmitStatus] = useState<string>("");
    const [serverErrorMessage, setServerErrorMessage] = useState<string>(
        "An unexpected error occurred on the server, please try again."
    );

    const handleSubmit = async (
        values: GameRequest,
        actions: FormikHelpers<GameRequest>
    ) => {
        console.log("Submit Game");
        await sleep(1000);
        const result = validationSchema.validate(values, {abortEarly: false}).catch((err) => err);
        if (result instanceof ValidationError) {
            setSubmitStatus("ERROR");
            actions.setSubmitting(false);
        } else {
            setSubmitStatus("PENDING");
            const gameResponse: GameDetails | ServerErrorResponse =
                await createGame(values);
            if (isServerErrorResponse(gameResponse)) {
                setSubmitStatus("SERVER_ERROR");
                setServerErrorMessage(gameResponse.message);
                console.log("Error placing order", gameResponse);
            } else {
                const gameDetails = gameResponse as GameDetails;
                console.log("game details", gameDetails);
                setSubmitStatus("OK");
                //navigate
            }
        }
        console.log(JSON.stringify(values, null, 2));
    };

    function isServerErrorResponse(response: any): response is ServerErrorResponse {
        return response && typeof response === 'object' && 'error' in response;
    }

    return (
        <div className="container">
            <section>
                <Formik
                    initialValues={initialValues}
                    validationSchema={validationSchema}
                    validateOnBlur={true}
                    validateOnChange={false}
                    onSubmit={handleSubmit}
                >
                    {({isSubmitting, values, isValid}) => (
                        <Form className="game-form">
                            <TextInput label="Game Name" name="name"/>
                            <div className="game-div">
                                <FieldArray name="categoryRequests">
                                    {() => (
                                        <>
                                            {values.categoryRequests.map((_category, index) => (
                                                <CategoryFormItem key={index} index={index}/>
                                            ))}
                                        </>
                                    )}
                                </FieldArray>
                            </div>

                            <div className="submit-game">
                                <button
                                    type="submit"
                                    disabled={isSubmitting || !isValid || submitStatus === 'PENDING'}>
                                    {isSubmitting ?
                                        <FontAwesomeIcon icon={faSpinner} spin/> : 'Submit Game'
                                    }
                                </button>
                            </div>
                        </Form>
                    )}
                </Formik>
            </section>

            {submitStatus != '' && (
                <section className="submit-status-box">
                    {submitStatus === 'ERROR' &&
                        <div>Error: Please fix the problems above and try again.</div>}
                    {submitStatus === 'PENDING' && <div>Processing...</div>}
                    {submitStatus === 'OK' && <div>Game created...</div>}
                    {submitStatus === "SERVER_ERROR" && serverErrorMessage}
                </section>
            )}
        </div>
    );
}

export default GameFormItem;